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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.banner.Banner
import com.github.banner.transformer.DepthPageTransformer
import com.github.banner.transformer.ZoomOutPageTransformer
import com.qfxl.samples.R
import com.qfxl.samples.mockBasicColorData

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
            render(R.layout.banner_item_tansform, mockBasicColorData()) { position, t ->
                getView<TextView>(R.id.tv_transform_page)?.apply {
                    setBackgroundColor(t)
                    text = position.toString()
                }
            }
        }

        findViewById<Banner>(R.id.banner_transform_2).apply {
            supportMultiPage()
            render(R.layout.banner_item_tansform, mockBasicColorData()) { position, t ->
                getView<TextView>(R.id.tv_transform_page)?.apply {
                    setBackgroundColor(t)
                    text = position.toString()
                }
            }
        }

        findViewById<Banner>(R.id.banner_transform_3).apply {
            supportMultiScalePage(minScale = 0.7f)
            render(R.layout.banner_item_tansform, mockBasicColorData()) { position, t ->
                getView<TextView>(R.id.tv_transform_page)?.apply {
                    setBackgroundColor(t)
                    text = position.toString()
                }
            }
        }
        findViewById<Banner>(R.id.banner_transform_4).apply {
            setPageTransformer(DepthPageTransformer())
            render(R.layout.banner_item_tansform, mockBasicColorData()) { position, t ->
                getView<TextView>(R.id.tv_transform_page)?.apply {
                    setBackgroundColor(t)
                    text = position.toString()
                }
            }
        }

        findViewById<Banner>(R.id.banner_transform_5).apply {
            setPageTransformer(ZoomOutPageTransformer(0.9f, 0.8f))
            render(R.layout.banner_item_tansform, mockBasicColorData()) { position, t ->
                getView<TextView>(R.id.tv_transform_page)?.apply {
                    setBackgroundColor(t)
                    text = position.toString()
                }
            }
        }

    }
}