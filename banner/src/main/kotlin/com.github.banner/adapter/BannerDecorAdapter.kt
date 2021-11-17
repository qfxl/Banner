package com.github.banner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

class BannerDecorAdapter<T>(val realAdapter: RecyclerView.Adapter<BaseBannerViewHolder>) :
    RecyclerView.Adapter<BaseBannerViewHolder>() {

    var enableLoop = false

    companion object {
        /**
         * if enableLoop, adapter will return realCount*ratio
         */
        private const val FAKE_PAGE_RATIO = 300
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBannerViewHolder {
        return realAdapter.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseBannerViewHolder, position: Int) {
        realAdapter.onBindViewHolder(holder, position % realAdapter.itemCount)
    }

    override fun getItemCount(): Int {
        return if (enableLoop && realAdapter.itemCount > 1) {
            realAdapter.itemCount * FAKE_PAGE_RATIO
        } else {
            realAdapter.itemCount
        }
    }
}