package com.github.banner.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.Banner
import kotlin.math.abs

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/19
 * desc   :
 * version: 1.0
 */

class ScaleInTransform(private val minScale: Float, private val orientation: Int) :
    ViewPager2.PageTransformer {
    init {
        require(orientation == Banner.HORIZONTAL || orientation == Banner.VERTICAL) { "Orientation must be Banner.HORIZONTAL or Banner.VERTICAL." }
    }

    override fun transformPage(view: View, position: Float) {
        view.elevation = -abs(position)
        val pageWidth = view.width
        val pageHeight = view.height

        view.pivotY = (pageHeight / 2).toFloat()
        view.pivotX = (pageWidth / 2).toFloat()
        if (position < -1) {
            view.scaleX = minScale
            view.scaleY = minScale
            if (orientation == Banner.HORIZONTAL) {
                view.pivotX = pageWidth.toFloat()
            } else {
                view.pivotY = pageHeight.toFloat()
            }
        } else if (position <= 1) {
            if (position < 0) {
                val scaleFactor = (1 + position) * (1 - minScale) + minScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                if (orientation == Banner.HORIZONTAL) {
                    view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
                } else {
                    view.pivotY = pageHeight * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
                }
            } else {
                val scaleFactor = (1 - position) * (1 - minScale) + minScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                if (orientation == Banner.HORIZONTAL) {
                    view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
                } else {
                    view.pivotY = pageHeight * ((1 - position) * DEFAULT_CENTER)
                }
            }
        } else {
            view.pivotX = 0f
            view.scaleX = minScale
            view.scaleY = minScale
        }
    }

    companion object {
        const val DEFAULT_CENTER = 0.5f
    }
}