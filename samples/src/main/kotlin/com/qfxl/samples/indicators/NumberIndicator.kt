package com.qfxl.samples.indicators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.github.banner.dp
import com.github.banner.indicator.BaseIndicator
import com.github.banner.sp
import kotlin.math.max

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/23
 * desc   :
 * version: 1.0
 */

class NumberIndicator(context: Context) : BaseIndicator(context) {
    private val bgPaint by lazy(LazyThreadSafetyMode.NONE) {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#99999999")
        }
    }

    private val defaultText = "0/0"

    private val defaultPadding = 6.dp

    init {
        indicatorPaint.apply {
            color = Color.WHITE
            textSize = 14f.sp
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minWidth = indicatorPaint.measureText(defaultText) + defaultPadding * 2
        val fm = indicatorPaint.fontMetrics
        val minHeight = fm.descent - fm.ascent + defaultPadding * 2

        val measuredSize = max(minWidth, minHeight)
        setMeasuredDimension(measuredSize.toInt(), measuredSize.toInt())
    }

    override fun drawHorizontal(canvas: Canvas?) {
        canvas?.apply {
            drawCircle(width / 2f, height / 2f, width / 2f, bgPaint)
            drawText("${currentPage+1}/$itemCount", width / 2f, height / 2f - (indicatorPaint.descent() + indicatorPaint.ascent()) / 2, indicatorPaint)
        }
    }

    override fun drawVertical(canvas: Canvas?) {
        canvas?.apply {
            drawCircle(width / 2f, height / 2f, width / 2f, bgPaint)
            drawText("${currentPage+1}/$itemCount", width / 2f, height / 2f - (indicatorPaint.descent() + indicatorPaint.ascent()) / 2, indicatorPaint)
        }
    }
}