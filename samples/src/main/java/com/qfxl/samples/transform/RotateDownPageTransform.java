package com.qfxl.samples.transform;


import android.view.View;

import com.qfxl.view.pagetransform.BasePageTransform;

/**
 * @author qfxl
 */
public class RotateDownPageTransform extends BasePageTransform {
    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;
    @Override
    public void handleInvisiblePage(View view, float position) {
        if (position < -1) {
            view.setRotation(mMaxRotate * -1);
            view.setPivotX(view.getWidth());
            view.setPivotY(view.getHeight());
        } else {
            view.setRotation(mMaxRotate);
            view.setPivotX(0);
            view.setPivotY(view.getHeight());
        }
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setPivotX(view.getWidth() * (0.5f + 0.5f * (-position)));
        view.setPivotY(view.getHeight());
        view.setRotation(mMaxRotate * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setPivotX(view.getWidth() * 0.5f * (1 - position));
        view.setPivotY(view.getHeight());
        view.setRotation(mMaxRotate * position);
    }
}
