package com.github.banner.callback

import android.animation.ValueAnimator
import androidx.viewpager2.widget.ViewPager2

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/18
 * desc   : This class is used for custom scroll duration in ViewPager2,
 *          The principle is to use ViewPager2#fakeDragBy(Float)
 * version: 1.0
 */

class FakeDragAnimatorUpdateListener(private val vp: ViewPager2) :
    ValueAnimator.AnimatorUpdateListener {

    private var previousValue = 0

    override fun onAnimationUpdate(animator: ValueAnimator) {
        val currentValue = animator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        vp.fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }

    /**
     * reset for next term
     */
    fun reset() {
        previousValue = 0
    }
}