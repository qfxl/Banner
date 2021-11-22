package com.github.banner.callback

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/22
 * desc   : Interface definition for a callback to be invoked when banner item is clicked.
 * version: 1.0
 */

interface OnBannerItemClickListener {

    /**
     * on banner item click
     * @param position
     */
    fun onBannerItemClick(position: Int)
}