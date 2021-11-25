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

package com.qfxl.samples

import android.content.Context
import android.graphics.Color
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.qfxl.samples.adapter.QuickAdapter
import com.qfxl.samples.adapter.QuickViewHolder

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

fun Context.getDrawableIdByName(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}


fun <T> RecyclerView.renderAdapter(
    @LayoutRes itemLayoutId: Int,
    renderScope: QuickViewHolder.(T) -> Unit,
    @LayoutRes headerLayoutId: Int = 0,
    headerRenderScope: QuickViewHolder.() -> Unit = {},
    adapterScope: QuickAdapter<T>.() -> Unit,
) {
    adapter = QuickAdapter(itemLayoutId, renderScope, headerLayoutId, headerRenderScope).apply {
        adapterScope(this)
    }
}

fun mockBasicColorData() = listOf(
    Color.parseColor("#19CAAD"),
    Color.parseColor("#BEEDC7"),
    Color.parseColor("#D1BA74"),
    Color.parseColor("#ECAD9E"),
    Color.parseColor("#BEE7E9")
)