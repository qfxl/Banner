package com.qfxl.samples

import android.content.Context

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   :
 * version: 1.0
 */

fun Context.getDrawableIdByName(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}