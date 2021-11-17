package com.github.banner

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

class SmoothLinearLayoutManager(context: Context, duration: Int = 0) :
    LinearLayoutManager(context) {

    private val mScroller by lazy(LazyThreadSafetyMode.NONE) {
        object : LinearSmoothScroller(context) {
            override fun calculateTimeForDeceleration(dx: Int): Int {
                return if (duration > 0) duration else super.calculateTimeForDeceleration(dx)
            }
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        mScroller.apply {
            targetPosition = position
            startSmoothScroll(mScroller)
        }
    }
}