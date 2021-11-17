package com.github.banner.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : Break down ViewPager2.PageTransformer's api
 * version: 1.0
 */

abstract class BasePageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                transformInvisiblePage(page, position)
            }
            position <= 0 -> { // [-1,0]
                // scroll to left page
                transformLeftPage(page, position)
            }
            position <= 1 -> { // (0,1]
                // scroll to right page
                transformRightPage(page, position)
            }
            else -> { // (1,+Infinity]
                // This page is way off-screen to the right.
                transformInvisiblePage(page, position)
            }
        }
    }

    /**
     * handle page for invisible
     * @param page target
     * @param position
     */
    abstract fun transformInvisiblePage(page: View, position: Float)

    /**
     * handle page scroll to left
     * @param page target
     * @param position
     */
    abstract fun transformLeftPage(page: View, position: Float)

    /**
     * handle page scroll to right
     * @param page target
     * @param position
     */
    abstract fun transformRightPage(page: View, position: Float)
}