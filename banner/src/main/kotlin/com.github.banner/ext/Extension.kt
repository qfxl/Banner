package com.github.banner

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/19
 * desc   : Extensions
 * version: 1.0
 */
inline val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Int.dp
    get() = this.toFloat().dp.toInt()

inline val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Int.sp
    get() = this.toFloat().sp


fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun String.bannerLog() {
    Log.d(Banner.TAG, this)
}

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
    return typedValue.data
}