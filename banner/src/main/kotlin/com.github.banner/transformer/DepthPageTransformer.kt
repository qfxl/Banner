package com.github.banner.transformer

import android.view.View
import kotlin.math.abs

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : DepthPageTransformer
 * version: 1.0
 */

class DepthPageTransformer(private val minScale: Float = MIN_SCALE) : BasePageTransformer() {
    companion object {
        private const val MIN_SCALE = 0.8f
    }

    override fun transformInvisiblePage(page: View, position: Float) {
        page.alpha = 0f
    }

    override fun transformLeftPage(page: View, position: Float) {
        page.apply {
            alpha = 1f
            translationX = 0f
            translationZ = 0f
            scaleX = 1f
            scaleY = 1f
        }
    }

    override fun transformRightPage(page: View, position: Float) {
        page.apply {
            // Fade the page out.
            alpha = 1 - position

            // Counteract the default slide transition
            translationX = width * -position
            // Move it behind the left page
            translationZ = -1f

            // Scale the page down (between MIN_SCALE and 1)
            val scaleFactor = (minScale + (1 - minScale) * (1 - abs(position)))
            scaleX = scaleFactor
            scaleY = scaleFactor
        }
    }
}