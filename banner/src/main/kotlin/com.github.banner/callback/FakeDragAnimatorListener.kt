package com.github.banner.callback

import android.animation.Animator
import androidx.viewpager2.widget.ViewPager2

/**
 * author : qfxl
 * e-mail : xuyonghong0822@gmail.com
 * time   : 2021/11/17
 * desc   : ViewPager2 fakeDrag animator listener
 * version: 1.0
 */

class FakeDragAnimatorListener(private val vp: ViewPager2) : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {
        vp.beginFakeDrag()
    }

    override fun onAnimationEnd(animation: Animator?) {
        vp.endFakeDrag()
    }

    override fun onAnimationCancel(animation: Animator?) {

    }

    override fun onAnimationRepeat(animation: Animator?) {

    }
}