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
import com.github.banner.*
import kotlin.math.max
import kotlin.math.min

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/20
 * desc   : basic realization provides a circular indicator.
 * version: 1.0
 */

class CircleSmoothIndicator(context: Context) : BaseIndicator(context) {

    /**
     * indicator radius
     */
    var radius = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (orientation == Banner.HORIZONTAL) {
            val measuredWidth =
                (itemCount - 1) * itemSpace + itemCount * itemWidth
            val measuredHeight = max(itemWidth, itemHeight)
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else {
            val measuredWidth = max(itemWidth, itemHeight)
            val measuredHeight = (itemCount - 1) * itemSpace + itemCount * itemHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = max(itemWidth, itemHeight) / 2f
    }

    override fun drawHorizontal(canvas: Canvas?) {
        var left = 0f
        for (index in 0 until itemCount) {
            indicatorPaint.color = defaultColor
            canvas?.drawCircle(left + radius, height / 2f, radius, indicatorPaint)
            left += itemWidth + itemSpace
        }
        var offsetLeft = radius + (itemSpace + radius * 2) * (currentPage + offset)
        offsetLeft = min(offsetLeft, width - radius)
        indicatorPaint.color = selectedColor
        canvas?.drawCircle(offsetLeft, height / 2f, radius, indicatorPaint)
    }

    override fun drawVertical(canvas: Canvas?) {
        var top = 0f
        var offsetTop = radius + (itemSpace + radius * 2) * (currentPage + offset)
        for (index in 0 until itemCount) {
            indicatorPaint.color = defaultColor
            canvas?.drawCircle(width / 2f, top + radius, radius, indicatorPaint)
            top += itemHeight + itemSpace
        }
        offsetTop = min(offsetTop, height - radius)
        indicatorPaint.color = selectedColor
        canvas?.drawCircle(width / 2f, offsetTop, radius, indicatorPaint)
    }

    override fun setDefaultValue() {
        if (itemWidth == 0) {
            itemWidth = 6.dp
        }
        if (itemHeight == 0) {
            itemHeight = 6.dp
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

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        currentPage = position
        invalidate()
    }
}