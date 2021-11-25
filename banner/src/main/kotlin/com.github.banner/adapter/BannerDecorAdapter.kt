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

package com.github.banner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.banner.callback.OnBannerItemClickListener

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : This class is a wrapper class of Banner#Adapter.
 *          In order to achieve an infinite loop,
 *          the principle of implementation is to multiply realItemCount by a coefficient when getItemCount.
 * version: 1.0
 */

class BannerDecorAdapter(val realAdapter: RecyclerView.Adapter<BannerViewHolder>) :
    RecyclerView.Adapter<BannerViewHolder>() {

    var enableInfinityLoop = false

    var onBannerItemClickListener: OnBannerItemClickListener? = null

    companion object {
        /**
         * if enableInfinityLoop, adapter getItemCount() will return realCount*ratio
         */
        private const val FAKE_PAGE_RATIO = 300
    }

    override fun getItemViewType(position: Int): Int {
        return realAdapter.getItemViewType(position % realAdapter.itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return realAdapter.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        realAdapter.onBindViewHolder(holder, position % realAdapter.itemCount)
        holder.itemView.setOnClickListener {
            onBannerItemClickListener?.onBannerItemClick(position % realAdapter.itemCount)
        }
    }

    override fun getItemCount(): Int {
        return if (enableInfinityLoop && realAdapter.itemCount > 1) {
            realAdapter.itemCount * FAKE_PAGE_RATIO
        } else {
            realAdapter.itemCount
        }
    }
}