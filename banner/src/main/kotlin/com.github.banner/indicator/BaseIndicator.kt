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
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.github.banner.Banner
import com.github.banner.callback.OnBannerPageChangeCallback

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
    var currentPage = 0

    /**
     * indicator offset
     */
    var offset = 0f

    /**
     * indicator offset pixels
     */
    var offsetPixels = 0

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
        offsetPixels = positionOffsetPixels
    }

    override fun onPageSelected(position: Int) {
        currentPage = position
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (orientation == Banner.HORIZONTAL) {
            drawHorizontal(canvas)
        } else {
            drawVertical(canvas)
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
     */
    abstract fun drawHorizontal(canvas: Canvas?)

    /**
     * draw vertical indicators
     * @param canvas
     */
    abstract fun drawVertical(canvas: Canvas?)
}