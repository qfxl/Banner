/*
 *
 *  * Copyright (C)  XU YONGHONG, Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.github.banner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import com.github.banner.Banner
import com.github.banner.R
import com.github.banner.dp
import com.github.banner.themeColor
import kotlin.math.min

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   : basic realization provides a round rect indicator.
 * version: 1.0
 */

class RoundRectSmoothIndicator(context: Context) : BaseIndicator(context) {

    var roundRadius = 0

    private val indicatorRect by lazy(LazyThreadSafetyMode.NONE) {
        RectF()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (orientation == Banner.HORIZONTAL) {
            val measuredWidth = (itemCount - 1) * itemSpace + itemCount * itemWidth
            setMeasuredDimension(measuredWidth, itemHeight)
        } else {
            val measuredHeight = (itemCount - 1) * itemSpace + itemCount * itemWidth
            setMeasuredDimension(itemHeight, measuredHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (orientation == Banner.HORIZONTAL) {
            indicatorRect.top = 0f
            indicatorRect.bottom = itemHeight.toFloat()
        } else {
            indicatorRect.left = 0f
            indicatorRect.right = itemHeight.toFloat()
        }
    }

    override fun drawHorizontal(canvas: Canvas?) {
        var left = 0f
        for (index in 0 until itemCount) {
            indicatorRect.left = left
            indicatorRect.right = left + itemWidth
            indicatorPaint.color = defaultColor
            canvas?.drawRoundRect(
                indicatorRect,
                roundRadius.toFloat(),
                roundRadius.toFloat(),
                indicatorPaint
            )
            left += itemWidth + itemSpace
        }
        indicatorPaint.color = selectedColor
        var offsetLeft = (itemWidth + itemSpace) * (currentPage + offset)
        offsetLeft = min(offsetLeft, (width - itemWidth).toFloat())
        indicatorRect.left = offsetLeft
        indicatorRect.right = offsetLeft + itemWidth
        canvas?.drawRoundRect(
            indicatorRect,
            roundRadius.toFloat(),
            roundRadius.toFloat(),
            indicatorPaint
        )
    }

    override fun drawVertical(canvas: Canvas?) {
        var top = 0f
        for (index in 0 until itemCount) {
            indicatorRect.top = top
            indicatorRect.bottom = top + itemWidth
            indicatorPaint.color = defaultColor
            canvas?.drawRoundRect(
                indicatorRect,
                roundRadius.toFloat(),
                roundRadius.toFloat(),
                indicatorPaint
            )
            top += itemWidth + itemSpace
        }
        indicatorPaint.color = selectedColor
        var offsetTop = (itemWidth + itemSpace) * (currentPage + offset)
        offsetTop = min(offsetTop, (height - itemWidth).toFloat())
        indicatorRect.top = offsetTop
        indicatorRect.bottom = offsetTop + itemWidth
        canvas?.drawRoundRect(
            indicatorRect,
            roundRadius.toFloat(),
            roundRadius.toFloat(),
            indicatorPaint
        )
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
        if (roundRadius == 0) {
            roundRadius = itemWidth shr 1
        }
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        currentPage = position
        invalidate()
    }
}