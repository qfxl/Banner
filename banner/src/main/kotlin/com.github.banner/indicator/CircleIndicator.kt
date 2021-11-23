package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import com.github.banner.Banner
import com.github.banner.dp
import com.github.banner.themeColor
import com.qfxl.view.R
import kotlin.math.max

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   : basic realization provides a circular indicator.
 * version: 1.0
 */

class CircleIndicator(context: Context) : BaseIndicator(context) {

    /**
     * indicator radius
     */
    var radius = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val requiredItemWidth = max(itemWidth, itemSelectWidth)
        val requiredItemHeight = max(itemHeight, itemSelectHeight)
        if (orientation == Banner.HORIZONTAL) {
            val measuredWidth =
                (itemCount - 1) * itemSpace + itemCount * requiredItemWidth
            val measuredHeight = max(requiredItemWidth, requiredItemHeight)
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            val measuredWidth = max(requiredItemWidth, requiredItemHeight)
            val measuredHeight = (itemCount - 1) * itemSpace + itemCount * requiredItemHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = max(itemWidth, itemHeight) / 2f
    }

    override fun drawHorizontal(canvas: Canvas?, currentPage: Int, offset: Float) {
        var left = 0f
        for (index in 0 until itemCount) {
            if (index == currentPage) {
                radius = itemSelectWidth / 2f
                indicatorPaint.color = selectedColor
            } else {
                radius = itemWidth / 2f
                indicatorPaint.color = defaultColor
            }
            canvas?.drawCircle(left + radius, height / 2f, radius, indicatorPaint)
            left += if (index == currentPage) {
                itemSelectWidth + itemSpace
            } else {
                itemWidth + itemSpace
            }
        }
    }

    override fun drawVertical(canvas: Canvas?, currentPage: Int, offset: Float) {
        var top = 0f
        for (index in 0 until itemCount) {
            if (index == currentPage) {
                radius = itemSelectWidth / 2f
                indicatorPaint.color = selectedColor
            } else {
                radius = itemWidth / 2f
                indicatorPaint.color = defaultColor
            }
            canvas?.drawCircle(width / 2f, top + radius, radius, indicatorPaint)
            top += if (index == currentPage) {
                itemSelectHeight + itemSpace
            } else {
                itemHeight + itemSpace
            }
        }
    }

    override fun setDefaultValue() {
        if (itemWidth == 0) {
            itemWidth = 6.dp
        }
        if (itemSelectWidth == 0) {
            itemSelectWidth = itemWidth
        }
        if (itemHeight == 0) {
            itemHeight = 6.dp
        }
        if (itemSelectHeight == 0) {
            itemSelectHeight = itemHeight
        }
        if (itemSpace == 0) {
            itemSpace = 4.dp
        }
        if (defaultColor == 0) {
            defaultColor = Color.WHITE
        }
        if (selectedColor == 0) {
            selectedColor = context.themeColor(R.attr.colorPrimary)
        }
    }
}