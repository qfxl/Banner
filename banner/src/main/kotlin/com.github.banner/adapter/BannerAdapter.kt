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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : This class is the base class of Banner#Adapter, the purpose is to unify ViewHolder to BaseBannerViewHolder.
 * version: 1.0
 */

class BannerAdapter<T>(
    private val layoutId: Int,
    private val renderScope: BannerViewHolder.(Int, T) -> Unit
) : RecyclerView.Adapter<BannerViewHolder>() {

    private val dataList by lazy(LazyThreadSafetyMode.NONE) {
        ArrayList<T>()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun getItem(position: Int): T {
        return dataList[position]
    }

    fun submitList(data: List<T>) {
        if (dataList.isNotEmpty()) {
            dataList.clear()
        }
        dataList.addAll(data)
    }

    fun addItem(t: T) {
        dataList.add(t)
        notifyItemInserted(itemCount - 1)
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getCurrentList() = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        renderScope(holder, position, getItem(position))
    }

}