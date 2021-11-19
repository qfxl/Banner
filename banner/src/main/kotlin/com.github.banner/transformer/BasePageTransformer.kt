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
        startTransform(page, position)
        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                transformInvisiblePage(page, position)
            }
            position <= 0 -> { // [-1,0]
                // scroll to previous page
                transformPreviousPage(page, position)
            }
            position <= 1 -> { // (0,1]
                // scroll to next page
                transformNextPage(page, position)
            }
            else -> { // (1,+Infinity]
                // This page is way off-screen to the right.
                transformInvisiblePage(page, position)
            }
        }
    }

    /**
     * Apply a property transformation to the given page.
     *
     *@param page – Apply the transformation to this page
     *@param position – Position of page relative to the current front-and-center position of the pager.
     * 0 is front and center. 1 is one full page position to the right,
     * and -2 is two pages to the left. Minimum / maximum observed values depend on how many pages we keep attached,
     * which depends on offscreenPageLimit
     */
    open fun startTransform(page: View, position: Float) {}

    /**
     * handle page for invisible
     * @param page target
     * @param position
     */
    open fun transformInvisiblePage(page: View, position: Float) {}

    /**
     * handle page scroll to previous
     * @param page target
     * @param position
     */
    open fun transformPreviousPage(page: View, position: Float) {}

    /**
     * handle page scroll to next
     * @param page target
     * @param position
     */
    open fun transformNextPage(page: View, position: Float) {}
}