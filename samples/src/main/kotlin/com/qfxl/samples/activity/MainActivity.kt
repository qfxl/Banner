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

}

fun <T> Activity.next(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}