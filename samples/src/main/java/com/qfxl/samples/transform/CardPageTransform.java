package com.qfxl.samples.transform;

import android.view.View;

import com.qfxl.view.pagetransform.BasePageTransform;


/**
 * @author qfxl
 */
public class CardPageTransform extends BasePageTransform {
    private static final float MIN_SCALE = 0.70f;
    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setScaleX(MIN_SCALE);
        view.setScaleY(MIN_SCALE);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setScaleX(1 + 0.3f * position);
        view.setScaleY(1 + 0.3f * position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setScaleX(1 - 0.3f * position);
        view.setScaleY(1 - 0.3f * position);
    }
}
