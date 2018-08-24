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
package com.qfxl.view.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : 默认指示器
 *     version: 1.0
 * </pre>
 */
public class DefaultIndicator extends BaseIndicator {
    /**
     * 指示器集合
     */
    private List<ImageView> indicatorList;
    /**
     * 是否已经绑定了viewPager
     */
    private boolean isAttached;
    /**
     * 正常的资源id
     */
    private int normalResId;
    /**
     * 被选中的资源id
     */
    private int selectResId;
    /**
     * 指示器默认的宽
     */
    private int indicatorItemWidth;
    /**
     * 指示器默认高
     */
    private int indicatorItemHeight;
    /**
     * 指示器默认的间距
     */
    private int indicatorItemMargin;

    public DefaultIndicator(Context context) {
        this(context, null);
    }

    public DefaultIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        indicatorList = new ArrayList<>();
    }

    /**
     * 设置正常的指示器资源id
     *
     * @param resId
     */
    public void setIndicatorNormalResId(int resId) {
        normalResId = resId;
    }

    /**
     * 设置指示器选中的资源id
     *
     * @param resId
     */
    public void setIndicatorSelectResId(int resId) {
        selectResId = resId;
    }

    /**
     * 设置指示器宽度
     *
     * @param indicatorItemWidth
     */
    public void setIndicatorItemWidth(int indicatorItemWidth) {
        this.indicatorItemWidth = indicatorItemWidth;
    }

    /**
     * 设置指示器高度
     *
     * @param indicatorItemHeight
     */
    public void setIndicatorItemHeight(int indicatorItemHeight) {
        this.indicatorItemHeight = indicatorItemHeight;
    }

    /**
     * 设置指示器的外间距
     *
     * @param itemMargin
     */
    public void setIndicatorItemMargin(int itemMargin) {
        indicatorItemMargin = itemMargin;
    }

    @Override
    protected void onItemSelected(int position) {
        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == position) {
                indicatorList.get(i).setImageResource(selectResId);
            } else {
                indicatorList.get(i).setImageResource(normalResId);
            }
        }
    }

    @Override
    protected void createIndicators(int itemCount) {
        if (isAttached && indicatorList != null) {
            indicatorList.clear();
            removeAllViews();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(indicatorItemWidth == 0 ? LayoutParams.WRAP_CONTENT : indicatorItemWidth,
                indicatorItemHeight == 0 ? LayoutParams.WRAP_CONTENT : indicatorItemHeight);
        lp.setMargins(indicatorItemMargin, indicatorItemMargin, indicatorItemMargin, indicatorItemMargin);
        for (int i = 0; i < itemCount; i++) {
            ImageView mImageView = new ImageView(getContext());
            mImageView.setLayoutParams(lp);
            if (i == 0) {
                mImageView.setImageResource(selectResId);
            } else {
                mImageView.setImageResource(normalResId);
            }
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(mImageView);
            indicatorList.add(mImageView);
        }
        isAttached = true;
    }
}
