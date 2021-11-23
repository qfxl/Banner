package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.github.banner.Banner
import com.github.banner.dp
import com.github.banner.themeColor
import com.github.banner.R
import kotlin.math.max

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   : basic realization provides a rect indicator.
 * version: 1.0
 */

class RectIndicator(context: Context) : BaseIndicator(context) {

    private val indicatorRect by lazy(LazyThreadSafetyMode.NONE) {
        RectF()
    }

    init {
        indicatorPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val requiredItemWidth = max(itemWidth, itemSelectWidth)
        val requiredItemHeight = max(requiredItemWidth, max(itemHeight, itemSelectHeight))
        if (orientation == Banner.HORIZONTAL) {
            val measuredWidth = (itemCount - 1) * (itemSpace + itemWidth) + itemSelectWidth
            setMeasuredDimension(measuredWidth, requiredItemHeight)
        } else {
            val measuredHeight = (itemCount - 1) * (itemSpace + itemWidth) + itemSelectWidth
            setMeasuredDimension(requiredItemWidth, measuredHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (orientation == Banner.HORIZONTAL) {
            indicatorRect.top = 0f
        } else {
            indicatorRect.left = 0f
        }
    }

    override fun drawHorizontal(canvas: Canvas?) {
        var left = 0f
        for (index in 0 until itemCount) {
            indicatorRect.left = left
            if (index == currentPage) {
                indicatorRect.bottom = itemSelectHeight.toFloat()
                indicatorRect.right = left + itemSelectWidth
                indicatorPaint.color = selectedColor
            } else {
                indicatorRect.bottom = itemHeight.toFloat()
                indicatorRect.right = left + itemWidth
                indicatorPaint.color = defaultColor
            }
            canvas?.drawRect(indicatorRect, indicatorPaint)
            left += if (index == currentPage) {
                itemSelectWidth + itemSpace
            } else {
                itemWidth + itemSpace
            }
        }
    }

    override fun drawVertical(canvas: Canvas?) {
        var top = 0f
        for (index in 0 until itemCount) {
            indicatorRect.top = top
            if (index == currentPage) {
                indicatorRect.right = itemSelectHeight.toFloat()
                indicatorRect.bottom = top + itemSelectWidth
                indicatorPaint.color = selectedColor
            } else {
                indicatorRect.right = itemHeight.toFloat()
                indicatorRect.bottom = top + itemWidth
                indicatorPaint.color = defaultColor
            }
            canvas?.drawRect(indicatorRect, indicatorPaint)
            top += if (index == currentPage) {
                itemSelectWidth + itemSpace
            } else {
                itemWidth + itemSpace
            }
        }
    }

    override fun setDefaultValue() {
        if (itemWidth == 0) {
            itemWidth = 10.dp
        }
        if (itemSelectWidth == 0) {
            itemSelectWidth = itemWidth
        }
        if (itemHeight == 0) {
            itemHeight = 2.dp
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