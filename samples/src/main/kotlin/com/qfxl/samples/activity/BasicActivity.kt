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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.banner.Banner
import com.qfxl.samples.R
import com.qfxl.samples.mockBasicColorData

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
            render(R.layout.banner_item_basic, mockBasicColorData()) { position, t ->
                itemView.setBackgroundColor(t)
                (itemView as TextView).text = position.toString()
            }
        }

        findViewById<Banner>(R.id.banner_basic_2).render(
            R.layout.banner_item_basic,
            mockBasicColorData()
        ) { position, t ->
            itemView.setBackgroundColor(t)
            (itemView as TextView).text = position.toString()
        }

        findViewById<Banner>(R.id.banner_basic_3).render(
            R.layout.banner_item_image,
            listOf(
                "https://i0.hippopx.com/photos/320/918/427/sky-clouds-sunlight-dark-preview.jpg",
                "https://i0.hippopx.com/photos/242/966/413/dawn-sun-mountain-landscape-preview.jpg",
                "https://i0.hippopx.com/photos/794/70/509/home-office-workstation-office-business-preview.jpg"
            )
        ) { position, t ->
            Glide.with(itemView).load(t).into(itemView as ImageView)
        }
    }
}