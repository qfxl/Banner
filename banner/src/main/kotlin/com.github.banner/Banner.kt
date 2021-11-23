package com.github.banner

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.*
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
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
import com.github.banner.callback.OnBannerItemClickListener
import com.github.banner.callback.OnBannerPageChangeCallback
import com.github.banner.indicator.BaseIndicator
import com.github.banner.indicator.CircleIndicator
import com.github.banner.indicator.RectIndicator
import com.github.banner.indicator.RoundRectIndicator
import com.github.banner.transformer.OverlapSliderTransformer
import com.github.banner.transformer.ScaleInTransform
import com.qfxl.view.R
import java.util.*
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


        const val INDICATOR_NONE = 0
        const val INDICATOR_CIRCLE = 1
        const val INDICATOR_RECT = 2
        const val INDICATOR_ROUND_RECT = 3

        const val ALIGN_PARENT_START = RelativeLayout.ALIGN_PARENT_START
        const val ALIGN_PARENT_TOP = RelativeLayout.ALIGN_PARENT_TOP
        const val ALIGN_PARENT_END = RelativeLayout.ALIGN_PARENT_END
        const val ALIGN_PARENT_BOTTOM = RelativeLayout.ALIGN_PARENT_BOTTOM
        const val CENTER_HORIZONTAL = RelativeLayout.CENTER_HORIZONTAL
        const val CENTER_VERTICAL = RelativeLayout.CENTER_VERTICAL
        const val CENTER_IN_PARENT = RelativeLayout.CENTER_IN_PARENT
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
     * enable infinity loop or not,
     * this is actually an illusion,it is just multiplied by 300 when returning itemCount.
     */
    var enableInfinityLoop = true
        set(value) {
            require(bannerCore.adapter == null) { "Banner Adapter is not null, you must invoke reset() first!" }
            field = value
        }

    /**
     * enable auto scroll or not
     */
    var enableAutoScroll = true

    /**
     * auto scroll when attached or not
     */
    var autoScrollWhenAttached = true

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
    private var mOnBannerPageChangeCallback: OnBannerPageChangeCallback? = null

    /**
     * banner item click listener
     */
    private var mOnBannerItemClickListener: OnBannerItemClickListener? = null

    /* -- for Gesture conflict -- */
    private var mDownX = 0f
    private var mDownY = 0f

    /* -- for default supported indicators -- */
    private var indicatorItemWidth = 0
    private var indicatorItemHeight = 0
    private var indicatorItemSelectWidth = 0
    private var indicatorItemSelectHeight = 0
    private var indicatorItemSpace = 0
    private var indicatorRoundRectRadius = 0

    @ColorInt
    private var indicatorDefaultColor = 0

    @ColorInt
    private var indicatorSelectColor = 0
    private var mIndicator: BaseIndicator? = null
    private val indicatorLayoutRules by lazy(LazyThreadSafetyMode.NONE) {
        LinkedList<Int>()
    }
    private var indicatorMarginStart = 0
    private var indicatorMarginTop = 0
    private var indicatorMarginEnd = 0
    private var indicatorMarginBottom = 0

    init {
        with(context.obtainStyledAttributes(attrs, R.styleable.Banner)) {
            bannerCore.apply {
                isUserInputEnabled =
                    getBoolean(R.styleable.Banner_banner_userInputEnable, enableUserInput)
                offscreenPageLimit =
                    getInt(
                        R.styleable.Banner_banner_pageOffscreenLimit,
                        this@Banner.offscreenPageLimit
                    )
                enableAutoScroll = getBoolean(R.styleable.Banner_banner_enableAutoLoop, true)
                autoScrollWhenAttached =
                    getBoolean(R.styleable.Banner_banner_autoScrollWhenAttached, true)
                enableInfinityLoop = getBoolean(R.styleable.Banner_banner_enableInfinityLoop, true)
                autoScrollDuration =
                    getInt(R.styleable.Banner_banner_autoScrollDuration, 500).toLong()
                autoScrollInterval =
                    getInt(
                        R.styleable.Banner_banner_autoScrollInterval,
                        DEFAULT_AUTO_SCROLL_INTERVAL.toInt()
                    ).toLong()
                pageMargin = getInt(R.styleable.Banner_banner_pageMargin, 0)

                indicatorDefaultColor = getColor(R.styleable.Banner_banner_indicatorDefaultColor, 0)
                indicatorSelectColor = getColor(R.styleable.Banner_banner_indicatorSelectColor, 0)
                indicatorItemWidth =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemWidth, 0)
                indicatorItemHeight =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemHeight, 0)
                indicatorItemSelectWidth =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemSelectWidth, 0)
                indicatorItemSelectHeight =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemSelectHeight, 0)
                indicatorItemSpace =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemSpace, 0)
                indicatorRoundRectRadius =
                    getDimensionPixelOffset(R.styleable.Banner_banner_indicatorRoundRectRadius, 0)
                val indicatorStyle =
                    getInt(R.styleable.Banner_banner_indicatorStyle, INDICATOR_NONE)
                val alignParentStart =
                    getBoolean(R.styleable.Banner_banner_indicatorAlignParentStart, false)
                if (alignParentStart) {
                    indicatorLayoutRules.add(ALIGN_PARENT_START)
                }
                val alignParentTop =
                    getBoolean(R.styleable.Banner_banner_indicatorAlignParentTop, false)
                if (alignParentTop) {
                    indicatorLayoutRules.add(ALIGN_PARENT_TOP)
                }
                val alignParentEnd =
                    getBoolean(R.styleable.Banner_banner_indicatorAlignParentEnd, false)
                if (alignParentEnd) {
                    indicatorLayoutRules.add(ALIGN_PARENT_END)
                }
                val alignParentBottom =
                    getBoolean(R.styleable.Banner_banner_indicatorAlignParentBottom, false)
                if (alignParentBottom) {
                    indicatorLayoutRules.add(ALIGN_PARENT_BOTTOM)
                }
                val centerHorizontal =
                    getBoolean(R.styleable.Banner_banner_indicatorCenterHorizontal, false)
                if (centerHorizontal) {
                    indicatorLayoutRules.add(CENTER_HORIZONTAL)
                }
                val centerVertical =
                    getBoolean(R.styleable.Banner_banner_indicatorCenterVertical, false)
                if (centerVertical) {
                    indicatorLayoutRules.add(CENTER_VERTICAL)
                }
                val centerInParent =
                    getBoolean(R.styleable.Banner_banner_indicatorCenterInParent, false)
                if (centerInParent) {
                    indicatorLayoutRules.add(CENTER_IN_PARENT)
                }
                indicatorMarginStart =
                    getDimensionPixelOffset(R.styleable.Banner_banner_marginStart, 8.dp)
                indicatorMarginTop =
                    getDimensionPixelOffset(R.styleable.Banner_banner_marginTop, 8.dp)
                indicatorMarginEnd =
                    getDimensionPixelOffset(R.styleable.Banner_banner_marginEnd, 8.dp)
                indicatorMarginBottom =
                    getDimensionPixelOffset(R.styleable.Banner_banner_marginBottom, 8.dp)

                mIndicator = when (indicatorStyle) {
                    INDICATOR_CIRCLE -> CircleIndicator(context)
                    INDICATOR_RECT -> RectIndicator(context)
                    INDICATOR_ROUND_RECT -> RoundRectIndicator(context)
                    else -> {
                        null
                    }
                }
            }
            orientation = getInt(R.styleable.Banner_banner_orientation, HORIZONTAL)
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
     * register lifecycle owner manually
     * if Banner#Context is LifecycleOwner, it will automatically register.
     * @param owner
     */
    fun registerLifecycleOwner(owner: LifecycleOwner) {
        if (context is LifecycleOwner) {
            Log.d(
                TAG,
                "registerLifecycleOwner failed, observer is already attached to ${context.javaClass.simpleName}!"
            )
        } else {
            owner.lifecycle.addObserver(this)
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
        val decorAdapter = BannerDecorAdapter(adapter).also { decor ->
            decor.enableInfinityLoop = enableInfinityLoop
            decor.onBannerItemClickListener = mOnBannerItemClickListener
        }
        bannerCore.adapter = decorAdapter
        if (enableInfinityLoop) {
            setCurrentItem(0, false)
        }
        //create indicators
        createIndicators()
        if (autoScrollWhenAttached) {
            startAutoScroll()
        }
    }

    /**
     * get ViewPager2#adapter
     * @return ViewPagers#Adapter
     */
    fun getAdapter() = bannerCore.adapter

    /**
     * start auto scroll, Banner will automatically switch to the next page, if is looping, do nothing.
     */
    fun startAutoScroll() {
        if (enableAutoScroll) {
            if (isLooping) {
                Log.w(TAG, "Banner is already looping.")
            } else {
                looping()
                isLooping = true
            }
        }
    }

    /**
     * stop auto scroll
     */
    fun stopAutoScroll() {
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
                    //fake drag will delay onPageSelect, invoke manually.
                    postDelayed({
                        mIndicator?.onPageSelected(getRealPosition(nextItem))
                    }, autoScrollDuration / 2)

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
            require(adapter is BannerDecorAdapter) { "Banner adapter must be BannerDecorAdapter!" }
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
        mOnBannerPageChangeCallback = callback
    }

    /**
     * set banner itemClickListener
     * @param itemClickAction
     */
    fun setOnBannerItemClickListener(itemClickAction: (Int) -> Unit) {
        mOnBannerItemClickListener = object : OnBannerItemClickListener {
            override fun onBannerItemClick(position: Int) {
                itemClickAction(position)
            }
        }
        bannerCore.adapter?.also {
            if (it is BannerDecorAdapter) {
                it.onBannerItemClickListener = mOnBannerItemClickListener
            }
        }
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
                    stopAutoScroll()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                    startAutoScroll()
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
        mOnBannerPageChangeCallback?.onPageScrolled(
            getRealPosition(position),
            positionOffset,
            positionOffsetPixels
        )
        mIndicator?.onPageScrolled(
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
        mOnBannerPageChangeCallback?.onPageSelected(getRealPosition(position))
        mIndicator?.onPageSelected(getRealPosition(position))
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
        mIndicator?.onPageScrollStateChanged(state)
    }

    /**
     * If enableInfinityLoop is turned on, position needs to be subtracted by the corresponding coefficient.
     * @param position currentPosition
     * @return real position
     */
    private fun getRealPosition(position: Int): Int {
        return bannerCore.adapter?.let { adapter ->
            require(adapter is BannerDecorAdapter) { "Banner adapter must be BannerDecorAdapter!" }
            if (enableInfinityLoop && adapter.itemCount > 0) {
                position % adapter.realAdapter.itemCount
            } else {
                position
            }
        } ?: position
    }

    /* -- indicators -- */

    /**
     * create Banner indicators
     */
    private fun createIndicators(layoutParams: LayoutParams? = null) {
        mIndicator?.apply {
            defaultColor = indicatorDefaultColor
            selectedColor = indicatorSelectColor
            itemWidth = indicatorItemWidth
            itemSelectWidth = indicatorItemSelectWidth
            itemHeight = indicatorItemHeight
            itemSelectHeight = indicatorItemSelectHeight
            itemSpace = indicatorItemSpace
            if (this is RoundRectIndicator) {
                roundRadius = indicatorRoundRectRadius
            }
            setDefaultValue()
            orientation = this@Banner.orientation
            bannerCore.adapter?.also { adapter ->
                require(adapter is BannerDecorAdapter) { "Banner adapter must be BannerDecorAdapter!" }
                itemCount = adapter.realAdapter.itemCount
            }
            addView(
                this,
                layoutParams ?: LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
                ).also { lp ->
                    indicatorLayoutRules.forEach { rule ->
                        lp.addRule(rule)
                    }
                    lp.setMargins(
                        indicatorMarginStart,
                        indicatorMarginTop,
                        indicatorMarginEnd,
                        indicatorMarginBottom
                    )
                })
        }
    }

    /**
     * set indicators, If the default indicator effect cannot be satisfied,
     * you can call this method to set the indicator inherited from BaseIndicator
     * @param indicator
     * @param layoutParams
     */
    fun setIndicators(indicator: BaseIndicator, layoutParams: LayoutParams? = null) {
        indicator.apply {
            mIndicator = this
            orientation = this@Banner.orientation
            bannerCore.adapter?.also { adapter ->
                require(adapter is BannerDecorAdapter) { "Banner adapter must be BannerDecorAdapter!" }
                itemCount = adapter.realAdapter.itemCount
            }
        }
        getAdapter()?.apply {
            createIndicators(layoutParams)
        }
    }

    /**
     * add indicator layout rule, This is consistent with the rule usage of RelativeLayout
     * @param rule
     */
    fun addIndicatorLayoutRule(rule: Int) {
        require(
            rule == ALIGN_PARENT_START
                    || rule == ALIGN_PARENT_TOP
                    || rule == ALIGN_PARENT_END
                    || rule == ALIGN_PARENT_BOTTOM
                    || rule == CENTER_HORIZONTAL
                    || rule == CENTER_VERTICAL
                    || rule == CENTER_IN_PARENT
        ) { "unsupported rule $rule" }
        indicatorLayoutRules.add(rule)
        mIndicator?.parent?.let {
            mIndicator?.layoutParams = mIndicator?.layoutParams?.also { lp ->
                if (lp is LayoutParams) {
                    indicatorLayoutRules.forEach { rule ->
                        lp.addRule(rule)
                    }
                }
            }
        }
    }

    /**
     * remove indicator layout rule, This is consistent with the rule usage of RelativeLayout
     * @param rule
     */
    fun removeIndicatorLayoutRule(rule: Int) {
        require(
            rule == ALIGN_PARENT_START
                    || rule == ALIGN_PARENT_TOP
                    || rule == ALIGN_PARENT_END
                    || rule == ALIGN_PARENT_BOTTOM
                    || rule == CENTER_HORIZONTAL
                    || rule == CENTER_VERTICAL
                    || rule == CENTER_IN_PARENT
        ) { "unsupported rule $rule" }
        indicatorLayoutRules.remove(rule)
    }

    /**
     * set indicator margins
     * @param start
     * @param top
     * @param end
     * @param bottom
     */
    fun setIndicatorMargins(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) {
        indicatorMarginStart = start
        indicatorMarginTop = top
        indicatorMarginEnd = end
        indicatorMarginBottom = bottom
        mIndicator?.let { indicator ->
            if (indicator.parent != null) {
                indicator.layoutParams = indicator.layoutParams.also {
                    if (it is LayoutParams) {
                        it.setMargins(
                            indicatorMarginStart,
                            indicatorMarginTop,
                            indicatorMarginEnd,
                            indicatorMarginBottom
                        )
                    }
                }
            }
        }
    }
    /* -- support page styles -- */

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
        stopAutoScroll()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        startAutoScroll()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        stopAutoScroll()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isLooping) {
            stopAutoScroll()
        }
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(INDICATOR_NONE, INDICATOR_CIRCLE, INDICATOR_RECT, INDICATOR_ROUND_RECT)
    annotation class IndicatorStyle
}