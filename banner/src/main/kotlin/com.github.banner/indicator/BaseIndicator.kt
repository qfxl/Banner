package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.github.banner.Banner
import com.github.banner.callback.OnBannerPageChangeCallback
import com.github.banner.dp
import kotlin.math.max

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

    var itemSpace = 4.dp

    var itemWidth = 8.dp

    var itemHeight = 8.dp

    var itemCount = 0

    var orientation = Banner.HORIZONTAL

    private var currentPage = 0

    private var offset = 0f

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (orientation == Banner.HORIZONTAL) {
            val measuredWidth = (itemCount - 1) * itemSpace + itemCount * itemWidth
            val measuredHeight = max(itemWidth, itemHeight)
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            val measuredWidth = max(itemWidth, itemHeight)
            val measuredHeight = (itemCount - 1) * itemSpace + itemCount * itemHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
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
     * draw horizontal indicators
     * @param canvas
     * @param currentBannerItem
     * @param offset
     */
    abstract fun drawHorizontal(canvas: Canvas?, currentBannerItem: Int, offset: Float)

    /**
     * draw vertical indicators
     * @param canvas
     * @param currentBannerItem
     * @param offset
     */
    abstract fun drawVertical(canvas: Canvas?, currentBannerItem: Int, offset: Float)
}