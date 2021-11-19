package com.github.banner.adapter

import androidx.annotation.LayoutRes

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   :
 * version: 1.0
 */

class BannerAdapter<T>(
    @LayoutRes private val itemLayoutId: Int,
    private val renderScope: BaseBannerViewHolder.(T) -> Unit
) : BaseBannerAdapter<T>() {
    override fun getLayoutId(viewType: Int): Int = itemLayoutId

    override fun bindViewHolder(holder: BaseBannerViewHolder, t: T) {
        renderScope(holder, t)
    }
}