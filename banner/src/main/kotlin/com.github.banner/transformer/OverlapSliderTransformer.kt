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

package com.github.banner.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.Banner
import com.github.banner.toPx
import kotlin.math.abs
import kotlin.math.max

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/19
 * desc   :
 * version: 1.0
 */

class OverlapSliderTransformer(
    private val orientation: Int,
    private val minScale: Float = 0f,
    private val unSelectedItemRotation: Float = 0f,
    private val unSelectedItemAlpha: Float = 0f,
    private val itemGap: Float = 0f
) : ViewPager2.PageTransformer {

    init {
        require(minScale in 0f..1f) { "minScale value should be between 1.0 to 0.0" }
        require(unSelectedItemAlpha in 0f..1f) { "unSelectedItemAlpha value should be between 1.0 to 0.0" }
    }

    private val scalingValue = 0.2f

    override fun transformPage(page: View, position: Float) {
        page.apply {
            elevation = -abs(position)

            val delta = max(1f - abs(position * (1 - 0.5f)), 0.5f)

            if (unSelectedItemRotation != 0f) {
                val rotation =
                    (1 - delta) * if (position > 0) unSelectedItemRotation else -unSelectedItemRotation

                rotationY = rotation
            }

            val scaleDelta = abs(position * scalingValue)
            val scale = max(1f - scaleDelta, minScale)

            scaleX = scale
            scaleY = scale

            when (orientation) {
                Banner.HORIZONTAL -> {
                    translationX =
                        position * (itemGap.toInt() / 2).toPx() +
                                if (position > 0) {
                                    (-width * (1f - scale))
                                } else {
                                    (width * (1f - scale))
                                }
                }
                Banner.VERTICAL -> {
                    translationY = position * (itemGap.toInt()).toPx() +
                            if (position > 0) {
                                (-width * (1f - scale))
                            } else {
                                (width * (1f - scale))
                            }
                }
                else -> throw IllegalArgumentException("Gives correct orientation value, Banner.HORIZONTAL or Banner.VERTICAL")
            }

            if (unSelectedItemAlpha != 1f) {
                alpha = when {
                    position >= -1f && position <= 1f -> { // (0, 1]
                        // page move from right to center.
                        0.5f + ((1 - abs(position)) * 0.5f)
                    }
                    else -> {
                        0.5f / abs(position * position)
                    }
                }
            }
        }
    }

}