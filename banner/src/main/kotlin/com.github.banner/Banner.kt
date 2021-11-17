package com.github.banner

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.adapter.BannerAdapter
import com.github.banner.adapter.BannerDecorAdapter
import com.github.banner.callback.FakeDragAnimatorListener
import com.github.banner.callback.OnBannerPageChangeCallback
import com.github.banner.transformer.BasePageTransformer
import com.qfxl.view.R

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/16
 * desc   :
 * version: 1.0
 */

class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {

    companion object {
        private const val TAG = "Banner"
    }

    private val bannerCore by lazy(LazyThreadSafetyMode.NONE) {
        ViewPager2(context)
    }

    /**
     * orientation
     */
    var bannerOrientation = ViewPager2.ORIENTATION_HORIZONTAL
        set(value) {
            field = value
            bannerCore.orientation = value
        }

    /**
     * enable user input or not
     */
    var enableUserInput = true
        set(value) {
            field = value
            bannerCore.isUserInputEnabled = value
        }

    /**
     * offscreenPageLimit
     */
    var offscreenPage = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        set(value) {
            field = value
            bannerCore.offscreenPageLimit = value
        }

    /**
     * loop interval
     */
    var loopInterval = 3000L

    /**
     * loop runnable
     */
    private val loopRunnable by lazy(LazyThreadSafetyMode.NONE) {
        Runnable {
            next()
            looping()
        }
    }

    /**
     * enable loop or not
     */
    var enableLoop = false
        set(value) {
            require(bannerCore.adapter == null) { "Banner Adapter is not null, you must invoke reset() first!" }
            field = value
        }

    /**
     * auto loop or not
     */
    var autoLoop = false

    /**
     * isLooping or not
     */
    private var isLooping = false

    /**
     * fake drag animator listener, for scroll duration
     */
    private val fakeDragAnimatorListener by lazy(LazyThreadSafetyMode.NONE) {
        FakeDragAnimatorListener(bannerCore)
    }

    /**
     * fake drag animator, for scroll duration
     */
    private val fakeDragAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator()
    }


    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.Banner)) {
            bannerCore.apply {
                orientation = bannerOrientation
                isUserInputEnabled =
                    getBoolean(R.styleable.Banner_banner_touchEnable, enableUserInput)
                offscreenPageLimit =
                    getInt(R.styleable.Banner_banner_pageOffscreenLimit, offscreenPage)
            }
            recycle()
        }
        addView(bannerCore)
    }

    /**
     * Set a new adapter to provide page views on demand.
     * Note that the adapter must implement BaseBannerAdapter
     * @param adapter
     */
    fun <T> setAdapter(adapter: BannerAdapter<T>) {
        val decorAdapter = BannerDecorAdapter<T>(adapter).also { decor ->
            decor.enableLoop = enableLoop
        }
        bannerCore.adapter = decorAdapter
        if (enableLoop) {
            setCurrentItem(0, false)
        }
        createIndicators()
        if (autoLoop) {
            startLoop()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isLooping) {
            stopLoop()
        }
    }

    /**
     * start loop，if is looping, do nothing.
     */
    fun startLoop() {
        if (isLooping) {
            Log.w(TAG, "Banner is already looping.")
        } else {
            looping()
            isLooping = true
        }
    }

    /**
     * stop loop
     */
    fun stopLoop() {
        if (isLooping) {
            removeCallbacks(loopRunnable)
        }
        isLooping = false
    }

    /**
     * looping
     */
    private fun looping() {
        postDelayed(loopRunnable, loopInterval)
    }

    /**
     * show next page
     */
    private fun next() {
        bannerCore.adapter?.also { adapter ->
            if (adapter.itemCount > 0) {
                val currentItem = bannerCore.currentItem
                var nextItem = 0
                if (currentItem < adapter.itemCount - 1) {
                    nextItem = currentItem + 1
                }
//                bannerCore.currentItem = nextItem
                val pxToDrag: Int = bannerCore.width * (nextItem - currentItem)
                val animator = ValueAnimator.ofInt(0, pxToDrag)
                var previousValue = 0
                animator.addUpdateListener { valueAnimator ->
                    val currentValue = valueAnimator.animatedValue as Int
                    val currentPxToDrag = (currentValue - previousValue).toFloat()
                    bannerCore.fakeDragBy(-currentPxToDrag)
                    previousValue = currentValue
                }
                animator.addListener(fakeDragAnimatorListener)
                animator.interpolator = AccelerateDecelerateInterpolator()
                animator.duration = 800L
                animator.start()
            }
        }
    }

    /**
     * Returns the currently selected page.
     * If no page can sensibly be selected because there is no adapter or the adapter is empty, returns 0.
     * Returns:
     * Currently selected page
     */
    fun getCurrentItem(): Int {
        return bannerCore.adapter?.let { adapter ->
            require(adapter is BannerDecorAdapter<*>) { "Banner adapter must be BannerDecorAdapter!" }
            if (enableLoop && adapter.itemCount > 0) {
                bannerCore.currentItem % adapter.realAdapter.itemCount
            } else {
                bannerCore.currentItem
            }
        } ?: 0
    }

    /**
     * Set the currently selected page.
     * If the ViewPager has already been through its first layout with its current adapter there will be a smooth animated
     * transition between the current item and the specified item. Silently ignored if the adapter is not set or empty.
     * Clamps item to the bounds of the adapter.
     * @param item Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    fun setCurrentItem(item: Int, smoothScroll: Boolean = true) {
        bannerCore.adapter?.also { adapter ->
            require(adapter is BannerDecorAdapter<*>) { "Banner adapter must be BannerDecorAdapter!" }
            if (adapter.itemCount > 0) {
                val currentItem = if (enableLoop) {
                    adapter.itemCount shr 1 + item % adapter.realAdapter.itemCount
                } else {
                    item
                }
                bannerCore.setCurrentItem(currentItem, smoothScroll)
            }
        }
    }

    /**
     * get bannerCore
     * @return bannerCore
     */
    fun getViewPager() = bannerCore

    /**
     * Sets a ViewPager2.PageTransformer that will be called for each attached page whenever the scroll position is changed.
     * This allows the application to apply custom property transformations to each page, overriding the default sliding behavior.
     * Note: setting a ViewPager2.PageTransformer disables data-set change animations to prevent conflicts between the two animation systems.
     * Setting a null transformer will restore data-set change animations.
     * @param transformer – PageTransformer that will modify each page's animation properties
     */
    fun setPageTransformer(transformer: BasePageTransformer?) {
        bannerCore.setPageTransformer(transformer)
    }

    /**
     * create Banner indicators
     */
    private fun createIndicators() {

    }

    /**
     * reset
     */
    fun reset() {
        bannerCore.adapter = null
    }

    /**
     * Add a callback that will be invoked whenever the page changes or is incrementally scrolled.
     * See ViewPager2.OnPageChangeCallback also.
     * Components that add a callback should take care to remove it when finished.
     * @param callback  callback to add
     */
    fun registerBannerPageCallback(callback: OnBannerPageChangeCallback) {
        bannerCore.registerOnPageChangeCallback(callback)
    }

    /**
     * Remove a callback that was previously added via registerOnPageChangeCallback(OnBannerPageChangeCallback)
     * @param callback – callback to remove
     */
    fun unregisterOnPageChangeCallback(callback: OnBannerPageChangeCallback) {
        bannerCore.unregisterOnPageChangeCallback(callback)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
}