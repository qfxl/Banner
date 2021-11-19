package com.qfxl.samples.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
import com.github.banner.adapter.BannerAdapter
import com.qfxl.samples.R

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

class BasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        findViewById<Banner>(R.id.banner_basic_1).apply {
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

        findViewById<Banner>(R.id.banner_basic_2).apply {
            orientation = Banner.VERTICAL
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
}