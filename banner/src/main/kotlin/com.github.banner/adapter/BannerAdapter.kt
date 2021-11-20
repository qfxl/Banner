package com.github.banner.adapter

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   : This class is the basic implementation of BaseBannerAdapter
 * version: 1.0
 */

class BannerAdapter<T>(
    private val createLayoutId: (Int) -> Int,
    private val renderScope: BannerViewHolder.(Int, Int, T) -> Unit
) : BaseBannerAdapter<T>() {

    override fun getLayoutId(viewType: Int): Int = createLayoutId(viewType)

    override fun bindViewHolder(holder: BannerViewHolder, position: Int, t: T) {
        renderScope(holder, position, getItemViewType(position), t)
    }

}