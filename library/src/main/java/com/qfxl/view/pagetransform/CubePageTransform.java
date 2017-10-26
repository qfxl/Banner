package com.qfxl.view.pagetransform;

import android.view.View;


/**
 * @author qfxl
 */
public class CubePageTransform extends BasePageTransform {
    private final int ROTATION_RATIO = 90;

    @Override
    public void handleInvisiblePage(View view, float position) {

    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setPivotX(view.getMeasuredWidth());
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(ROTATION_RATIO * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        //设置旋转中心点
        view.setPivotX(0);
        view.setPivotY(view.getMeasuredHeight() * 0.5f);
        view.setRotationY(ROTATION_RATIO * position);
    }
}
