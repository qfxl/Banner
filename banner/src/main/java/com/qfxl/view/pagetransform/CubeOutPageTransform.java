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
 *     desc   : 切换动画示例CubeOut
 *     version: 1.0
 * </pre>
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
