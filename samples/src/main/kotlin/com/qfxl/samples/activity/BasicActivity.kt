package com.qfxl.samples.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
import com.github.banner.dp
import com.github.banner.render
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
            indicatorAlignParentStart = true
            render(R.layout.banner_item_basic, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_basic_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_basic_2).apply {
            orientation = Banner.VERTICAL
            indicatorCenterVertical = true
            indicatorAlignParentStart = true
            render(R.layout.banner_item_basic, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_basic_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_basic_3).apply {
            render(R.layout.banner_item_basic, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_basic_page)?.setImageResource(t)
            }
        }
    }
}