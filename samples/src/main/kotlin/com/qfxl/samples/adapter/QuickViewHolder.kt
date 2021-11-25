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

package com.qfxl.samples.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   :
 * version: 1.0
 */

class QuickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View> = SparseArray()

    fun <T : View> getView(@IdRes id: Int): T? {
        var v: View? = mViews.get(id)
        if (v == null) {
            v = itemView.findViewById(id)
            mViews.put(id, v)
        }
        return v as T?
    }

    companion object {
        operator fun get(parent: ViewGroup, layoutId: Int): QuickViewHolder {
            val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return QuickViewHolder(convertView)
        }
    }
}