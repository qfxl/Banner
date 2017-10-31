package com.qfxl.view.pagetransform;

import android.view.View;


/**
 * @author qfxl
 */
public class ZoomOutPageTransformer extends BasePageTransform {
    private final float MIN_SCALE = 0.85f;

    private final float MIN_ALPHA = 0.5f;

    @Override
    public void handleInvisiblePage(View view, float position) {
        view.setAlpha(0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = view.getHeight() * (1 - scaleFactor) / 2;
        float horzMargin = view.getWidth() * (1 - scaleFactor) / 2;
        view.setTranslationX(horzMargin - vertMargin / 2);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
    }

    @Override
    public void handleRightPage(View view, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float vertMargin = view.getHeight() * (1 - scaleFactor) / 2;
        float horzMargin = view.getWidth() * (1 - scaleFactor) / 2;

        view.setTranslationX(-horzMargin + vertMargin / 2);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
    }
}
