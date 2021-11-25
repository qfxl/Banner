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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.qfxl.samples.R

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onBasicClick(v: View) {
        next(BasicActivity::class.java)
    }

    fun onIndicatorClick(v: View) {
        next(IndicatorActivity::class.java)
    }

    fun onTransformClick(v: View) {
        next(TransformActivity::class.java)
    }

    fun onMultiPageClick(v: View) {
        next(MultiPageActivity::class.java)
    }
}

fun <T> Activity.next(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}