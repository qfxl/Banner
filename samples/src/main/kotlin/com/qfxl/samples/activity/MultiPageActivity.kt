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

package com.qfxl.samples.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.banner.Banner
import com.github.banner.adapter.BannerViewHolder
import com.github.banner.bannerLog
import com.qfxl.samples.R

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/24
 * desc   :
 * version: 1.0
 */

class MultiPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipage)

        findViewById<Banner>(R.id.banner_multi_page).apply {
            setAdapter(MultiPageAdapter())
        }
    }

    class MultiPageAdapter : RecyclerView.Adapter<BannerViewHolder>() {

        companion object {
            private const val VIEW_TYPE_IMAGE = 1
            private const val VIEW_TYPE_TEXT = 2
            private const val VIEW_TYPE_BACKGROUND = 3
        }

        override fun getItemViewType(position: Int): Int {
            return when (position) {
                0 -> VIEW_TYPE_IMAGE
                1 -> VIEW_TYPE_TEXT
                else -> VIEW_TYPE_BACKGROUND
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            val layoutId = when (viewType) {
                VIEW_TYPE_IMAGE -> {
                    R.layout.banner_item_multi_page_image
                }
                VIEW_TYPE_TEXT -> {
                    R.layout.banner_item_multi_page_text
                }
                else -> {
                    R.layout.banner_item_multi_page_background
                }
            }
            return BannerViewHolder(
                LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            )
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
            when (getItemViewType(position)) {
                VIEW_TYPE_IMAGE -> {
                    (holder.itemView as ImageView).setImageResource(R.drawable.img_banner)
                }
                VIEW_TYPE_TEXT -> {
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                    (holder.itemView as TextView).text = "This is a Text Page"
                }
                else -> {
                    holder.itemView.setBackgroundColor(Color.parseColor("#BEEDC7"))
                }
            }
        }

        override fun getItemCount(): Int {
            return 3
        }
    }
}