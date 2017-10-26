package com.qfxl.view.pagetransform;

import android.view.View;

/**
 * @author qfxl
 */
public class DepthPageTransform extends BasePageTransform {
    private final float MIN_SCALE = 0.75f;

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        view.setAlpha(1);
        view.setTranslationX(0);
        view.setScaleX(1);
        view.setScaleY(1);
    }

    @Override
    public void handleRightPage(View view, float position) {
        view.setAlpha(1 - position);
        view.setTranslationX(view.getWidth() * -position);
        float scaleFactor = MIN_SCALE
                + (1 - MIN_SCALE) * (1 - Math.abs(position));
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
    }
}
