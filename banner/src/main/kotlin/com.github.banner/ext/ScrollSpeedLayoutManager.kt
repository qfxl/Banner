package com.github.banner.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.Banner

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/24
 * desc   :
 * version: 1.0
 */

class ScrollSpeedLayoutManager(
    private val banner: Banner,
    originLayoutManager: LinearLayoutManager
) :
    LinearLayoutManager(banner.context, originLayoutManager.orientation, false) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State,
        position: Int
    ) {
        val scroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun calculateTimeForDeceleration(dx: Int): Int {
                return banner.autoScrollDuration.toInt()
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    companion object {
        fun inject(banner: Banner) {
            if (banner.autoScrollDuration < 500L) {
                return
            }
            try {
                val vp2 = banner.getViewPager()
                val recyclerView = banner.getRecyclerView()
                val injectLayoutManager = ScrollSpeedLayoutManager(
                    banner,
                    recyclerView.layoutManager as LinearLayoutManager
                )
                recyclerView.layoutManager = injectLayoutManager

                val vp2LayoutManagerField =
                    ViewPager2::class.java.getDeclaredField("mLayoutManager")
                vp2LayoutManagerField.isAccessible = true
                vp2LayoutManagerField.set(vp2, injectLayoutManager)

                val pageTransformAdapterField =
                    ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
                pageTransformAdapterField.isAccessible = true
                pageTransformAdapterField.get(vp2)?.let { adapter ->
                    val adapterLm = adapter::class.java.getDeclaredField("mLayoutManager")
                    adapterLm.isAccessible = true
                    adapterLm.set(adapter, injectLayoutManager)
                }

                ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
                    .let { scrollEventAdapterField ->
                        scrollEventAdapterField.isAccessible = true
                        scrollEventAdapterField.get(vp2)?.let { scrollEventAdapter ->
                            val scrollEventLm =
                                scrollEventAdapter::class.java.getDeclaredField("mLayoutManager")
                            scrollEventLm.isAccessible = true
                            scrollEventLm.set(scrollEventAdapter, injectLayoutManager)
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}