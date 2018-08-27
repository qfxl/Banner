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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.qfxl.view.banner.BannerView;


/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : 基础指示器
 *     version: 1.0
 * </pre>
 */
public abstract class BaseIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    /**
     * 关联的LoopViewPager
     */
    protected BannerView mBannerView;
    /**
     * item的总个数
     */
    protected int itemTotalCount;
    /**
     * ViewPager item选中监听
     */
    private OnPageSelectListener mOnPageSelectListener;

    public interface OnPageSelectListener {
        /**
         * ViewPager item选中监听
         *
         * @param position
         */
        void onItemSelected(int position);
    }


    public BaseIndicator(Context context) {
        this(context, null);
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置ViewPager的滑动监听
     *
     * @param onPageSelectListener
     */
    public void setOnPageSelectListener(OnPageSelectListener onPageSelectListener) {
        mOnPageSelectListener = onPageSelectListener;
    }

    /**
     * 关联LoopViewPager
     *
     * @param bannerView
     */
    public void setViewPager(BannerView bannerView) {
        if (bannerView == mBannerView) {
            return;
        }
        if (bannerView != null && bannerView.getAdapter() != null) {
            mBannerView = bannerView;
            mBannerView.addOnPageChangeListener(this);
            itemTotalCount = mBannerView.getDecorAdapter().getRealCount();
            //只有数量>1才允许设置指示器
            if (itemTotalCount > 1) {
                createIndicators(itemTotalCount);
            }
        }
    }

    /**
     * 获取item的总个数
     *
     * @return
     */
    public int getItemTotalCount() {
        return itemTotalCount;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        onItemScrolled(position % mBannerView.getDecorAdapter().getRealCount(), positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageSelectListener != null) {
            mOnPageSelectListener.onItemSelected(mBannerView.getCurrentRealItem());
        }
        onItemSelected(mBannerView.getCurrentRealItem());
    }

    /**
     * 选中item
     *
     * @param position 当前被选中item的位置
     */
    protected abstract void onItemSelected(int position);

    /**
     * 创建指示器
     *
     * @param itemCount item个数
     */
    protected abstract void createIndicators(int itemCount);

    /**
     * 监听item滑动的距离
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    protected void onItemScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
