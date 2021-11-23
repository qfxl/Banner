package com.qfxl.samples.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
import com.github.banner.render
import com.qfxl.samples.R
import com.qfxl.samples.indicators.NumberIndicator
import com.qfxl.samples.mockBasicColorData

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/23
 * desc   :
 * version: 1.0
 */

class IndicatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indicator)
        findViewById<Banner>(R.id.banner_indicator_1).apply {
            render(R.layout.banner_item_basic, mockBasicColorData()) { position, t ->
                itemView.setBackgroundColor(t)
            }
        }

        findViewById<Banner>(R.id.banner_indicator_2).apply {
            addIndicatorLayoutRule(Banner.ALIGN_PARENT_END)
            addIndicatorLayoutRule(Banner.ALIGN_PARENT_BOTTOM)
            setIndicators(NumberIndicator(context))
            render(R.layout.banner_item_basic, mockBasicColorData()) { position, t ->
                itemView.setBackgroundColor(t)
            }
        }
    }
}