package com.qfxl.samples

import android.content.Context
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