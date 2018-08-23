/*
 * Copyright (c) 2018 Frank Xu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qfxl.view.pagetransform;

import android.view.View;


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : ZoomOut
 *     version: 1.0
 * </pre>
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
        float verticalMargin = view.getHeight() * (1 - scaleFactor) / 2;
        float horizontalMargin = view.getWidth() * (1 - scaleFactor) / 2;
        view.setTranslationX(horizontalMargin - verticalMargin / 2);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
    }

    @Override
    public void handleRightPage(View view, float position) {
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
        float verticalMargin = view.getHeight() * (1 - scaleFactor) / 2;
        float horizontalMargin = view.getWidth() * (1 - scaleFactor) / 2;

        view.setTranslationX(-horizontalMargin + verticalMargin / 2);
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
    }
}
