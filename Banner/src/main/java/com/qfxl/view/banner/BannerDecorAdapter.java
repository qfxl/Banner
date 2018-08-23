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
package com.qfxl.view.banner;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : 默认适配器的包装，用于实现infiniteLoop
 *     version: 1.0
 * </pre>
 */
public class BannerDecorAdapter extends PagerAdapter {
    private PagerAdapter pagerAdapter;
    /**
     * 是否可以循环
     */
    private boolean enableInfinityLoop;
    /**
     * 实际数量的倍数
     */
    private final int RATIO = 1000;

    /**
     * 居中回调
     */
    interface LoopViewPagerCenterListener {
        /**
         * 居中
         */
        void center();
    }

    private LoopViewPagerCenterListener centerListener;
    /**
     * 是否已居中
     */
    private boolean hasCentered;

    public BannerDecorAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    /**
     * 设置居中回调
     *
     * @param centerListener
     */
    public void setLoopViewPagerCenterListener(LoopViewPagerCenterListener centerListener) {
        this.centerListener = centerListener;
    }

    /**
     * 设置是否可以循环
     *
     * @param enableInfinityLoop
     */
    public void setEnableInfinityLoop(boolean enableInfinityLoop) {
        this.enableInfinityLoop = enableInfinityLoop;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position;
        if (enableInfinityLoop && pagerAdapter.getCount() != 0) {
            realPosition = position % pagerAdapter.getCount();
        }
        return pagerAdapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position;
        if (enableInfinityLoop && pagerAdapter.getCount() != 0) {
            realPosition = position % pagerAdapter.getCount();
        }
        pagerAdapter.destroyItem(container, realPosition, object);
    }

    @Override
    public int getCount() {
        int count;
        if (enableInfinityLoop) {
            //adapter获取的数量必须>1才能开启循环
            if (pagerAdapter.getCount() > 1) {
                count = pagerAdapter.getCount() * RATIO;
            } else {
                count = pagerAdapter.getCount();
            }
        } else {
            count = pagerAdapter.getCount();
        }
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return pagerAdapter.isViewFromObject(view, object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (!hasCentered && enableInfinityLoop) {
            if (centerListener != null) {
                centerListener.center();
            }
        }
        hasCentered = true;
        pagerAdapter.finishUpdate(container);
    }

    public PagerAdapter getAdapter() {
        return pagerAdapter;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int realPosition = position;
        if (enableInfinityLoop && pagerAdapter.getCount() != 0) {
            realPosition = position % pagerAdapter.getCount();
        }
        return pagerAdapter.getPageTitle(realPosition);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return pagerAdapter.getItemPosition(object);
    }

    @Override
    public Parcelable saveState() {
        return pagerAdapter.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        pagerAdapter.restoreState(state, loader);
    }

    @Override
    public float getPageWidth(int position) {
        int realPosition = position;
        if (enableInfinityLoop) {
            realPosition = position % pagerAdapter.getCount();
        }
        return pagerAdapter.getPageWidth(realPosition);
    }

    /**
     * 获取实际的item个数
     *
     * @return
     */
    public int getRealCount() {
        return pagerAdapter.getCount();
    }

}
