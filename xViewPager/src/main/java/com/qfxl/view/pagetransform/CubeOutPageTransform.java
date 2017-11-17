package com.qfxl.view.pagetransform;

import android.view.View;


/**
 * @author qfxl
 */
public class CubeOutPageTransform extends BasePageTransform {

    @Override
    public void handleInvisiblePage(View view, float position) {

    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setPivotX(view.getWidth());
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setPivotX(0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);
    }
}
