package com.qfxl.samples.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
import com.github.banner.adapter.BannerAdapter
import com.github.banner.transformer.DepthPageTransformer
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
            enableLoop = true
            autoLoop = true
            setPageTransformer(DepthPageTransformer())
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_basic_1) { holder, t ->
                (holder.itemView as ImageView).setImageResource(t)
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