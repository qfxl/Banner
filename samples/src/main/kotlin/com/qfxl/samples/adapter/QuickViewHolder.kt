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