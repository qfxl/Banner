package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   :
 * version: 1.0
 */

class CircleIndicator(context: Context) : BaseIndicator(context) {
    var normalColor = Color.GRAY
    var selectedColor = Color.RED

    var radius = itemWidth / 2f

    private val indicatorPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint().apply {
            color = normalColor
        }
    }

    override fun drawHorizontal(canvas: Canvas?, currentBannerItem: Int, offset: Float) {
        var left = 0f
        for (index in 0 until itemCount) {
            indicatorPaint.color = if (index == currentBannerItem) {
                selectedColor
            } else {
                normalColor
            }
            canvas?.drawCircle(left + radius, height / 2f, radius, indicatorPaint)
            left += itemWidth + itemSpace
        }
    }

    override fun drawVertical(canvas: Canvas?, currentBannerItem: Int, offset: Float) {
        var top = 0f
        for (index in 0 until itemCount) {
            indicatorPaint.color = if (index == currentBannerItem) {
                selectedColor
            } else {
                normalColor
            }
            canvas?.drawCircle(width / 2f, top + radius, radius, indicatorPaint)
            top += itemHeight + itemSpace
        }
    }

}