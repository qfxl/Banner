package com.github.banner.callback

import androidx.viewpager2.widget.ViewPager2

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : decor for ViewPager2#OnPageChangeCallback, override nothing.
 * version: 1.0
 */

abstract class OnBannerPageChangeCallback : ViewPager2.OnPageChangeCallback() {

    /**
     * This method will be invoked when the current page is scrolled,
     * either as part of a programmatically initiated smooth scroll or a user initiated touch scroll.
     * @param position – Position index of the first page currently being displayed.
     * Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset – Value from [0, 1) indicating the offset from the page at position.
     *@param positionOffsetPixels – Value in pixels indicating the offset from position
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    /**
     * This method will be invoked when a new page becomes selected.
     * Animation is not necessarily complete.
     * @param position – Position index of the new selected page.
     */
    override fun onPageSelected(position: Int) {

    }

    /**
     * Called when the scroll state changes.
     * Useful for discovering when the user begins dragging,
     * when a fake drag is started, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     * state can be one of SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING or SCROLL_STATE_SETTLING.
     */
    override fun onPageScrollStateChanged(state: Int) {

    }
}