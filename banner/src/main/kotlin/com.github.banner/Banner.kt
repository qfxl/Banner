package com.github.banner

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.*
import android.widget.RelativeLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.adapter.BannerDecorAdapter
import com.github.banner.adapter.BaseBannerAdapter
import com.github.banner.callback.FakeDragAnimatorListener
import com.github.banner.callback.FakeDragAnimatorUpdateListener
import com.github.banner.callback.OnBannerPageChangeCallback
import com.github.banner.transformer.OverlapSliderTransformer
import com.github.banner.transformer.ScaleInTransform
import com.qfxl.view.R
import kotlin.math.abs

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
) : RelativeLayout(context, attrs, defStyle), LifecycleObserver {

    companion object {
        private const val TAG = "Banner"
        private const val DEFAULT_SCROLL_DURATION = 500L
        private const val DEFAULT_AUTO_SCROLL_INTERVAL = 3000L

        const val HORIZONTAL = ViewPager2.ORIENTATION_HORIZONTAL
        const val VERTICAL = ViewPager2.ORIENTATION_VERTICAL
    }

    private val bannerCore by lazy(LazyThreadSafetyMode.NONE) {
        ViewPager2(context)
    }

    /**
     * orientation
     */
    @ViewPager2.Orientation
    var orientation = HORIZONTAL
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
    var offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        set(value) {
            field = value
            bannerCore.offscreenPageLimit = value
        }

    /**
     * page margin
     */
    var pageMargin = 0
        set(value) {
            field = value
            if (field > 0) {
                setPageTransformer(MarginPageTransformer(field))
            }
        }

    /* -- for auto scroll -- */
    /**
     * auto scroll or not
     */
    var autoScroll = false

    /**
     * auto scroll duration, default is -1
     */
    var autoScrollDuration = DEFAULT_SCROLL_DURATION

    /**
     * isLooping or not
     */
    private var isLooping = false

    /**
     * loop interval
     */
    var autoScrollInterval = DEFAULT_AUTO_SCROLL_INTERVAL

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
     * enable infinity loop or not,
     * this is actually an illusion,it is just multiplied by 300 when returning itemCount.
     */
    var enableInfinityLoop = true
        set(value) {
            require(bannerCore.adapter == null) { "Banner Adapter is not null, you must invoke reset() first!" }
            field = value
        }

    /**
     * fake drag animator, for scroll duration
     */
    private val fakeDragAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator().apply {
            addListener(fakeDragAnimatorListener)
            addUpdateListener(fakeDragAnimatorUpdateListener)
        }
    }

    /**
     * fake drag animator listener, for scroll duration
     */
    private val fakeDragAnimatorListener by lazy(LazyThreadSafetyMode.NONE) {
        FakeDragAnimatorListener(bannerCore)
    }

    /**
     * fake drag animator update listener, for scroll duration
     */
    private val fakeDragAnimatorUpdateListener by lazy(LazyThreadSafetyMode.NONE) {
        FakeDragAnimatorUpdateListener(bannerCore)
    }
    /* -- for auto scroll end-- */

    /**
     * banner page change callback
     */
    private var onBannerPageChangeCallback: OnBannerPageChangeCallback? = null

    /* -- for Gesture conflict -- */
    private var mDownX = 0f
    private var mDownY = 0f


    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.Banner)) {
            bannerCore.apply {
                orientation = getInt(R.styleable.Banner_banner_orientation, this@Banner.orientation)
                isUserInputEnabled =
                    getBoolean(R.styleable.Banner_banner_userInputEnable, enableUserInput)
                offscreenPageLimit =
                    getInt(
                        R.styleable.Banner_banner_pageOffscreenLimit,
                        this@Banner.offscreenPageLimit
                    )
                autoScroll = getBoolean(R.styleable.Banner_banner_autoScroll, true)
                enableInfinityLoop = getBoolean(R.styleable.Banner_banner_infinityLoop, true)
                autoScrollDuration =
                    getInt(R.styleable.Banner_banner_autoScrollDuration, 500).toLong()
                autoScrollInterval =
                    getInt(
                        R.styleable.Banner_banner_autoScrollInterval,
                        DEFAULT_AUTO_SCROLL_INTERVAL.toInt()
                    ).toLong()
                pageMargin = getInt(R.styleable.Banner_banner_pageMargin, 0)
            }
            recycle()
        }

        bannerCore.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                onBannerPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                onBannerPageSelect(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                onBannerPageScrollStateChanged(state)
            }
        })

        getRecyclerView().overScrollMode = OVER_SCROLL_NEVER

        addView(bannerCore, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

        //register lifecycle
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(this)
        }
    }

    /**
     * set auto scroll interpolator, only used for auto scroll.
     * @param interpolator
     */
    fun setAutoScrollInterpolator(interpolator: Interpolator) {
        fakeDragAnimator.interpolator = interpolator
    }

    /**
     * Set a new adapter to provide page views on demand.
     * Note that the adapter must implement BaseBannerAdapter
     * @param adapter
     */
    fun <T> setAdapter(adapter: BaseBannerAdapter<T>) {
        val decorAdapter = BannerDecorAdapter<T>(adapter).also { decor ->
            decor.enableLoop = enableInfinityLoop
        }
        bannerCore.adapter = decorAdapter
        if (enableInfinityLoop) {
            setCurrentItem(0, false)
        }
        createIndicators()
        if (autoScroll) {
            startLoop()
        }
    }

    /**
     * get ViewPager2#adapter
     * @return ViewPagers#Adapter
     */
    fun getAdapter() = bannerCore.adapter

    /**
     * start loop，if is looping, do nothing.
     */
    fun startLoop() {
        if (isLooping) {
            Log.w(TAG, "Banner is already looping.")
        } else {
            if (enableInfinityLoop) {
                looping()
                isLooping = true
            }
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
        postDelayed(loopRunnable, autoScrollInterval)
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
                if (autoScrollDuration == 0L) {
                    bannerCore.currentItem = nextItem
                } else {
                    val pxToDrag: Int =
                        if (orientation == HORIZONTAL) {
                            bannerCore.width * (nextItem - currentItem) - getRecyclerView().paddingLeft - getRecyclerView().paddingRight
                        } else {
                            bannerCore.height * (nextItem - currentItem) - getRecyclerView().paddingTop - getRecyclerView().paddingTop
                        }
                    fakeDragAnimatorUpdateListener.reset()
                    fakeDragAnimator.apply {
                        setIntValues(0, pxToDrag)
                        duration = autoScrollDuration
                        start()
                    }
                }
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
        return getRealPosition(bannerCore.currentItem)
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
                val currentItem = if (enableInfinityLoop) {
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
     * get ViewPager2 inner RecyclerView
     * @return RecyclerView
     */
    fun getRecyclerView() = bannerCore.getChildAt(0) as RecyclerView

    /**
     * Sets a ViewPager2.PageTransformer that will be called for each attached page whenever the scroll position is changed.
     * This allows the application to apply custom property transformations to each page, overriding the default sliding behavior.
     * Note: setting a ViewPager2.PageTransformer disables data-set change animations to prevent conflicts between the two animation systems.
     * Setting a null transformer will restore data-set change animations.
     * @param transformer – PageTransformer that will modify each page's animation properties
     */
    fun setPageTransformer(transformer: ViewPager2.PageTransformer?) {
        bannerCore.setPageTransformer(transformer)
    }

    /**
     * reset
     */
    fun reset() {
        bannerCore.adapter = null
    }

    /**
     * Add a callback that will be invoked whenever the page changes or is incrementally scrolled.
     * @param callback callback to add
     */
    fun registerBannerPageCallback(callback: OnBannerPageChangeCallback) {
        onBannerPageChangeCallback = callback
    }

    /* -- Manually handle move conflicts - */

    /**
     * Manually handle nesting conflicts
     * @param ev MotionEvent
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val doNothingIntercept = !enableUserInput || (getAdapter()?.itemCount ?: 0) <= 1
        if (doNothingIntercept) {
            return super.onInterceptTouchEvent(ev)
        }
        ev?.let { event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mDownX = event.x
                    mDownY = event.y
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val disX = abs(event.x - mDownX)
                    val disY = abs(event.y - mDownY)
                    if (orientation == HORIZONTAL) {
                        interceptHorizontalGesture(event.x, disX, disY)
                    } else {
                        interceptVerticalGesture(event.y, disX, disY)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
                else -> {

                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * Prepare to distribute gestures. When ACTION_DOWN is obtained, looping is not allowed
     * @param ev MotionEvent
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.action?.let { action ->
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    if (enableInfinityLoop && isLooping) {
                        stopLoop()
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                    if (enableInfinityLoop && autoScroll) {
                        startLoop()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Determine whether to intercept horizontal gestures
     * If the current page is the first page, then only the sliding event to the left can be intercepted, and the sliding event to the right does not need to be intercepted.
     * If the current page is the last page, then only the right sliding event can be intercepted, and the left sliding event does not need to be intercepted.
     * If the current page is the middle page, then both left and right events need to be intercepted.
     * @param endX current X coordinate
     * @param disX horizontal moved distance
     * @param disY vertical moved distance
     */
    private fun interceptHorizontalGesture(endX: Float, disX: Float, disY: Float) {
        getAdapter()?.let { adapter ->
            if (disX > disY) {
                val currentItem = bannerCore.currentItem
                val itemCount = adapter.itemCount
                if (currentItem == 0 && endX - mDownX > 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    val disallowIntercept = currentItem != itemCount - 1 || endX - mDownX >= 0
                    parent.requestDisallowInterceptTouchEvent(disallowIntercept)
                }
            } else if (disY > disX) {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
    }

    /**
     * similar to #interceptHorizontalGesture, but the orientation is vertical.
     * @param endY current Y coordinate
     * @param disX horizontal moved distance
     * @param disY vertical moved distance
     */
    private fun interceptVerticalGesture(endY: Float, disX: Float, disY: Float) {
        getAdapter()?.let { adapter ->
            if (disY > disX) {
                val currentItem = bannerCore.currentItem
                val itemCount = adapter.itemCount
                if (currentItem == 0 && endY - mDownY > 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    val disallowIntercept = currentItem != itemCount - 1 || endY - mDownY >= 0
                    parent.requestDisallowInterceptTouchEvent(disallowIntercept)
                }
            } else if (disX > disY) {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
    }
    /* -- Manually handle move conflicts end - */

    /**
     * This method will be invoked when the current page is scrolled,
     * either as part of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *  @param position – Position index of the first page currently being displayed. Page position+1 will be visible if positionOffset is nonzero.
     *  @param positionOffset – Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels – Value in pixels indicating the offset from position.
     */
    private fun onBannerPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        onBannerPageChangeCallback?.onPageScrolled(
            getRealPosition(position),
            positionOffset,
            positionOffsetPixels
        )
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
     * @param position – Position index of the new selected page.
     */
    private fun onBannerPageSelect(position: Int) {
        onBannerPageChangeCallback?.onPageSelected(getRealPosition(position))
    }

    /**
     * Called when the scroll state changes.
     * Useful for discovering when the user begins dragging,
     * when a fake drag is started,
     * when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     * @param state can be one of SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or SCROLL_STATE_SETTLING.
     */
    private fun onBannerPageScrollStateChanged(@ViewPager2.ScrollState state: Int) {

    }

    /**
     * If the loop is turned on, position needs to be subtracted by the corresponding coefficient.
     * @param position currentPosition
     * @return real position
     */
    private fun getRealPosition(position: Int): Int {
        return bannerCore.adapter?.let { adapter ->
            require(adapter is BannerDecorAdapter<*>) { "Banner adapter must be BannerDecorAdapter!" }
            if (enableInfinityLoop && adapter.itemCount > 0) {
                position % adapter.realAdapter.itemCount
            } else {
                position
            }
        } ?: position
    }

    /**
     * create Banner indicators
     */
    private fun createIndicators() {

    }

    /**
     * support display overlap page
     * @param minScale In addition to the minimum zoom value of the current page,
     * it can be understood as a default value for the minimum scale.
     * The minimum scale value provided by OverlapSliderTransformer is 0.8f.
     * @param unSelectedItemRotation rotation of unselected pages
     * @param unSelectedItemAlpha alpha of unselected pages
     * @param itemGap item gap
     * @param revealWidthOrHeight The distance that can be displayed between the current page and other pages
     */
    fun supportPageOverlap(
        minScale: Float = 0.8f,
        unSelectedItemRotation: Float = 0f,
        unSelectedItemAlpha: Float = 1f,
        itemGap: Float = 0f,
        revealWidthOrHeight: Int = 20.dp
    ) {
        getRecyclerView().apply {
            if (orientation == HORIZONTAL) {
                setPadding(revealWidthOrHeight, 0, revealWidthOrHeight, 0)
            } else {
                setPadding(0, revealWidthOrHeight, 0, revealWidthOrHeight)
            }
            clipToPadding = false
        }
        val overlapTransformer = OverlapSliderTransformer(
            orientation,
            minScale,
            unSelectedItemRotation,
            unSelectedItemAlpha,
            itemGap
        )
        setPageTransformer(overlapTransformer)
    }

    /**
     * support one screen and multiple pages.
     * @param itemMargin each page margin
     * @param revealWidthOrHeight The distance that can be displayed between the current page and other pages
     */
    fun supportMultiPage(itemMargin: Int = 10.dp, revealWidthOrHeight: Int = 20.dp) {
        getRecyclerView().apply {
            if (orientation == HORIZONTAL) {
                setPadding(revealWidthOrHeight, 0, revealWidthOrHeight, 0)
            } else {
                setPadding(0, revealWidthOrHeight, 0, revealWidthOrHeight)
            }
            clipToPadding = false
        }
        setPageTransformer(MarginPageTransformer(itemMargin))
    }

    /**
     * support one screen and multiple pages with scale.
     * @param minScale page min scale
     * @param itemMargin each page margin
     * @param revealWidthOrHeight The distance that can be displayed between the current page and other pages
     */
    fun supportMultiScalePage(
        minScale: Float = 0.8f,
        itemMargin: Int = 10.dp,
        revealWidthOrHeight: Int = 20.dp
    ) {
        getRecyclerView().apply {
            if (orientation == HORIZONTAL) {
                setPadding(revealWidthOrHeight, 0, revealWidthOrHeight, 0)
            } else {
                setPadding(0, revealWidthOrHeight, 0, revealWidthOrHeight)
            }
            clipToPadding = false
        }
        setPageTransformer(CompositePageTransformer().apply {
            addTransformer(ScaleInTransform(minScale, orientation))
            addTransformer(MarginPageTransformer(itemMargin))
        })
    }

    /* -- for lifecycle -- */

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        stopLoop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        startLoop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        stopLoop()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isLooping) {
            stopLoop()
        }
    }

}