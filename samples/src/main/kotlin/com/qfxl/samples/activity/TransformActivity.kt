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
 * time   : 2021/11/19
 * desc   :
 * version: 1.0
 */

class TransformActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transform)

        findViewById<Banner>(R.id.banner_transform_1).apply {
            supportPageOverlap()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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

        findViewById<Banner>(R.id.banner_transform_2).apply {
            supportMultiPage()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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

        findViewById<Banner>(R.id.banner_transform_3).apply {
            supportMultiScalePage()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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

        findViewById<Banner>(R.id.banner_transform_4).apply {
            orientation = Banner.VERTICAL
            supportPageOverlap()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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

        findViewById<Banner>(R.id.banner_transform_5).apply {
            orientation = Banner.VERTICAL
            supportMultiPage()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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

        findViewById<Banner>(R.id.banner_transform_6).apply {
            orientation = Banner.VERTICAL
            supportMultiScalePage()
            setAdapter(BannerAdapter<Int>(R.layout.banner_item_tansform) { t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
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