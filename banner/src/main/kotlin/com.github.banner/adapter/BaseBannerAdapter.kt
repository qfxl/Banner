package com.github.banner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

abstract class BaseBannerAdapter<T> : RecyclerView.Adapter<BaseBannerViewHolder>() {

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

    fun getCurrentList() = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBannerViewHolder {
        return BaseBannerViewHolder(
            LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseBannerViewHolder, position: Int) {
        bindViewHolder(holder, getItem(position))
    }

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun bindViewHolder(holder: BaseBannerViewHolder, t: T)
}