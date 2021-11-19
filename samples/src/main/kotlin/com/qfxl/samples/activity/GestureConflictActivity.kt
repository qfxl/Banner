package com.qfxl.samples.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.banner.Banner
import com.github.banner.adapter.BannerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qfxl.samples.R
import com.qfxl.samples.renderAdapter
import java.util.*

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   :
 * version: 1.0
 */

class GestureConflictActivity : AppCompatActivity() {

    private val mViewPager2: ViewPager2 by lazy {
        findViewById(R.id.vp_gesture)
    }

    private val mBottomNavigation: BottomNavigationView by lazy {
        findViewById(R.id.nav_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture_conflict)
        mBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_item1 -> {
                    mViewPager2.currentItem = 0
                }
                R.id.navigation_item2 -> {
                    mViewPager2.currentItem = 1
                }
                R.id.navigation_item3 -> {
                    mViewPager2.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        mViewPager2.apply {
            adapter = ViewPager2FragmentAdapter()
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val selectId = when (position) {
                        1 -> {
                            R.id.navigation_item2
                        }
                        2 -> {
                            R.id.navigation_item3
                        }
                        else -> {
                            R.id.navigation_item1
                        }
                    }
                    mBottomNavigation.selectedItemId = selectId
                }
            })
        }
    }

    inner class ViewPager2FragmentAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return DemoFragment.newInstance(position)
        }

    }

    class DemoFragment : Fragment() {

        private val position by lazy {
            arguments?.getInt(ARG_POSITION) ?: 0
        }

        companion object {
            private const val ARG_POSITION = "position"
            fun newInstance(position: Int): DemoFragment {
                return DemoFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_POSITION, position)
                    }
                }
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val layoutId = when (position) {
                0 -> {
                    R.layout.fragment_demo1
                }
                1 -> {
                    R.layout.fragment_demo2
                }
                2 -> {
                    R.layout.fragment_demo2
                }
                else -> 0
            }
            if (layoutId == 0) {
                return null
            }
            return inflater.inflate(layoutId, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            when (position) {
                0 -> {
                    (view as RecyclerView).apply {
                        renderAdapter<String>(
                            R.layout.recycler_item_fragment_demo1,
                            renderScope = { t ->
                                getView<TextView>(R.id.tv_fragment_demo1)?.text = t
                            },
                            headerLayoutId = R.layout.recycler_item_fragment_demo1_header,
                            headerRenderScope = {
                                getView<Banner>(R.id.banner_recycler_item_fragment_demo1)?.apply {
                                    orientation = ViewPager2.ORIENTATION_VERTICAL
                                    setAdapter(BannerAdapter<Int>(R.layout.banner_item_basic) { t ->
                                        getView<ImageView>(R.id.iv_basic_page)?.setImageResource(t)
                                    }.apply {
                                        submitList(
                                            listOf(
                                                R.drawable.img_banner_0,
                                                R.drawable.img_banner_1,
                                                R.drawable.img_banner_2,
                                                R.drawable.img_banner_3,
                                                R.drawable.img_banner_4
                                            )
                                        )
                                    })
                                }
                            }) {
                            val dataList = LinkedList<String>()
                            repeat(20) {
                                dataList.add("This is Demo Position $it")
                            }
                            submitList(dataList)
                        }
                    }
                }
                1 -> {
                    view.findViewById<Banner>(R.id.banner_fragment_demo2).apply {
                        setAdapter(BannerAdapter<Int>(R.layout.banner_item_basic) { t ->
                            getView<ImageView>(R.id.iv_basic_page)?.setImageResource(t)
                        }.apply {
                            submitList(
                                listOf(
                                    R.drawable.img_banner_0,
                                    R.drawable.img_banner_1,
                                    R.drawable.img_banner_2,
                                    R.drawable.img_banner_3,
                                    R.drawable.img_banner_4
                                )
                            )
                        })
                    }
                }
                2 -> {
                    view.setBackgroundColor(Color.GRAY)
                }
            }
        }
    }
}