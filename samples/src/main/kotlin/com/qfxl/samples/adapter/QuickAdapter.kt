package com.qfxl.samples.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   :
 * version: 1.0
 */

class QuickAdapter<T>(
    private val itemLayoutId: Int,
    private val renderScope: QuickViewHolder.(T) -> Unit,
    private val headerLayoutId: Int = -1,
    private val headerRenderScope: QuickViewHolder.() -> Unit = {}
) : RecyclerView.Adapter<QuickViewHolder>() {

    private val dataList by lazy(LazyThreadSafetyMode.NONE) {
        ArrayList<T>()
    }

    override fun getItemCount(): Int {
        return if (headerLayoutId != 0) {
            dataList.size + 1
        } else {
            dataList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (headerLayoutId != 0 && position == 0) {
            return headerLayoutId
        }
        return super.getItemViewType(position)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickViewHolder {
        val layoutId = if (viewType == headerLayoutId) headerLayoutId else itemLayoutId
        return QuickViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int) {
        if (getItemViewType(position) == headerLayoutId) {
            headerRenderScope(holder)
        } else {
            renderScope(holder, getItem(position - 1))
        }

    }
}