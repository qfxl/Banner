package com.qfxl.view.pagetransform;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * @author qfxl
 */
public abstract class BasePageTransform implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            handleInvisiblePage(page,position);
        } else if (position <= 0) {
            // [-1,0]
            // Use the default slide transition when moving to the left page
            handleLeftPage(page,position);
        }else if (position <= 1) {
            // (0,1]
            handleRightPage(page,position);
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            handleInvisiblePage(page,position);
        }
    }

    /**
     * 处理page不可见
     * @param view
     * @param position
     */
    public abstract void handleInvisiblePage(View view, float position);

    /**
     *  ->
     * @param view
     * @param position
     */
    public abstract void handleLeftPage(View view, float position);
    /**
     *  <-
     * @param view
     * @param position
     */
    public abstract void handleRightPage(View view, float position);
}
