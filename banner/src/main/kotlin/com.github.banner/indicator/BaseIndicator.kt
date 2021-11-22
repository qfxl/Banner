package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.banner.Banner
import com.github.banner.callback.OnBannerPageChangeCallback
import com.github.banner.dp

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   :
 * version: 1.0
 */

abstract class BaseIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle), OnBannerPageChangeCallback {
    /**
     * gap between each indicator
     */
    var itemSpace = 0

    /**
     * width of each indicator
     */
    var itemWidth = 0

    /**
     * width of each selected indicator
     */
    var itemSelectWidth = itemWidth

    /**
     * height of each indicator
     */
    var itemHeight = 0

    /**
     * height of each selected indicator
     */
    var itemSelectHeight = itemHeight

    /**
     * number of indicators, this number is the realCount of Banner#adapter
     */
    var itemCount = 0

    /**
     * default color of the indicator
     */
    var defaultColor = 0

    /**
     * selected color of the indicator
     */
    var selectedColor = 0

    /**
     * orientation associated with Banner
     */
    var orientation = Banner.HORIZONTAL

    /**
     * current page
     */
    private var currentPage = 0

    /**
     * indicator offset
     */
    private var offset = 0f

    /**
     * indicator paint
     */
    val indicatorPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = defaultColor
        }
    }

    init {
        require(orientation == Banner.HORIZONTAL || orientation == Banner.VERTICAL) { "Indicator orientation must be Banner.HORIZONTAL or Banner.VERTICAL" }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        offset = positionOffset
    }

    override fun onPageSelected(position: Int) {
        currentPage = position
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (orientation == Banner.HORIZONTAL) {
            drawHorizontal(canvas, currentPage, offset)
        } else {
            drawVertical(canvas, currentPage, offset)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    /**
     * Check if need to set default values
     */
    open fun setDefaultValue() {

    }

    /**
     * draw horizontal indicators
     * @param canvas
     * @param currentPage
     * @param offset
     */
    abstract fun drawHorizontal(canvas: Canvas?, currentPage: Int, offset: Float)

    /**
     * draw vertical indicators
     * @param canvas
     * @param currentPage
     * @param offset
     */
    abstract fun drawVertical(canvas: Canvas?, currentPage: Int, offset: Float)
}