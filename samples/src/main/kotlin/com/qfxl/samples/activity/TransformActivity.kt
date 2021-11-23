package com.qfxl.samples.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
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
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_transform_2).apply {
            orientation = Banner.VERTICAL
            supportPageOverlap()
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_transform_3).apply {
            supportMultiPage()
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_transform_4).apply {
            orientation = Banner.VERTICAL
            supportMultiPage()
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }
        findViewById<Banner>(R.id.banner_transform_5).apply {
            supportMultiScalePage()
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }

        findViewById<Banner>(R.id.banner_transform_6).apply {
            orientation = Banner.VERTICAL
            supportMultiScalePage()
            render(R.layout.banner_item_tansform, listOf(
                R.drawable.img_banner_0,
                R.drawable.img_banner_1,
                R.drawable.img_banner_2,
                R.drawable.img_banner_3,
                R.drawable.img_banner_4
            )) { position, t ->
                getView<ImageView>(R.id.iv_transform_page)?.setImageResource(t)
            }
        }


    }
}