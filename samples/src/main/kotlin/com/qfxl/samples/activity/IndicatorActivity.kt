/*
 *
 *  * Copyright (C)  XU YONGHONG, Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.qfxl.samples.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
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
        findViewById<Banner>(R.id.banner_indicator_1).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }

        findViewById<Banner>(R.id.banner_indicator_2).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }
        findViewById<Banner>(R.id.banner_indicator_3).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }
        findViewById<Banner>(R.id.banner_indicator_4).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }
        findViewById<Banner>(R.id.banner_indicator_5).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }
        findViewById<Banner>(R.id.banner_indicator_6).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }

        findViewById<Banner>(R.id.banner_indicator_7).apply {
            addIndicatorLayoutRule(Banner.ALIGN_PARENT_END)
            addIndicatorLayoutRule(Banner.ALIGN_PARENT_BOTTOM)
            setIndicators(NumberIndicator(context))
            render(R.layout.banner_item_basic, mockBasicColorData()) { position, t ->
                itemView.setBackgroundColor(t)
            }
        }

        findViewById<Banner>(R.id.banner_indicator_8).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
        }
    }
}