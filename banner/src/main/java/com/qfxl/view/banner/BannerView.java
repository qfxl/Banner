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

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Field;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : ViewPager
 *     version: 1.0
 * </pre>
 */
public class BannerView extends ViewPager implements BannerDecorAdapter.LoopViewPagerCenterListener {
    /**
     * 是否可以循环(默认可以)
     */
    private boolean enableInfinityLoop;
    /**
     * 自动循环的间隔(默认3s)
     */
    private int loopInterval;
    /**
     * 设置循环时使用的适配器
     */
    private BannerDecorAdapter bannerDecorAdapter;
    /**
     * 是否自动滚动(默认不自动)
     */
    private boolean isAutoLoop;
    /**
     * viewPager是否可以手动滑动(默认可以滚动)
     */
    private boolean touchScrollable;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private PagerAdapter originAdapter;
    /**
     * 是否是自动滚动
     *
     * @return
     */
    public boolean isAutoLoop() {
        return isAutoLoop;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        originAdapter = adapter;
        if (adapter != null) {
            if (bannerDecorAdapter == null || bannerDecorAdapter.getAdapter() != adapter) {
                bannerDecorAdapter = new BannerDecorAdapter(adapter);
                bannerDecorAdapter.setEnableInfinityLoop(enableInfinityLoop);
                bannerDecorAdapter.setLoopViewPagerCenterListener(this);
                super.setAdapter(bannerDecorAdapter);
            }
        }
    }

    @Nullable
    @Override
    public PagerAdapter getAdapter() {
        return originAdapter;
    }

    /**
     * 设置是否可以循环
     *
     * @param enableInfinityLoop
     */
    public void setEnableInfinityLoop(boolean enableInfinityLoop) {
        //如果已经设置了适配器，则不允许设置此参数
        if (getAdapter() == null) {
            this.enableInfinityLoop = enableInfinityLoop;
        } else {
            Log.i(BannerView.class.getName(), "请在setAdapter之前调用setEnableInfinityLoop");
        }
    }

    /**
     * 设置是否自动滚动
     *
     * @param autoLoop
     */
    public void setAutoLoop(boolean autoLoop) {
        isAutoLoop = autoLoop;
    }

    /**
     * 设置轮播时间
     *
     * @param timeInMillis
     */
    public void setLoopInterval(int timeInMillis) {
        loopInterval = timeInMillis;
    }

    /**
     * 设置滑动速率
     *
     * @param duration
     */
    public void setScrollDuration(int duration) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            BannerScroller scroller = new BannerScroller(getContext());
            scroller.setScrollDuration(duration);
            mScroller.set(this, scroller);
        } catch (Exception e) {
            //ignore
        }
    }

    /**
     * 开始自动轮播任务
     */
    public void startLoop() {
        if (isAutoLoop) {
            stopLoop();
            postDelayed(intervalRunnable, loopInterval);
        }
    }

    /**
     * 停止自动轮播任务
     */
    public void stopLoop() {
        removeCallbacks(intervalRunnable);
    }

    /**
     * 停止自动轮播任务
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(intervalRunnable);
    }

    /**
     * 隐藏的时候stop
     *
     * @param changedView
     * @param visibility
     */
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (isAutoLoop) {
            if (visibility == VISIBLE) {
                startLoop();
            } else {
                stopLoop();
            }
        }
    }

    /**
     * 自动轮播runnable
     */
    Runnable intervalRunnable = new Runnable() {
        @Override
        public void run() {
            if (getAdapter() != null && getAdapter().getCount() > 1) {
                next();
                startLoop();
            }
        }
    };

    /**
     * 获取设置的适配器
     *
     * @return
     */
    public BannerDecorAdapter getDecorAdapter() {
        return bannerDecorAdapter;
    }

    /**
     * 显示下一个item
     */
    private void next() {
        if (bannerDecorAdapter != null && bannerDecorAdapter.getCount() > 0) {
            int curr = super.getCurrentItem();
            int nextPage = 0;
            if (curr < bannerDecorAdapter.getCount() - 1) {
                nextPage = curr + 1;
            }
            super.setCurrentItem(nextPage, true);
        }
    }

    @Override
    public void center() {
        if (bannerDecorAdapter != null) {
            setCurrentItem(0);
        }
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (bannerDecorAdapter != null && bannerDecorAdapter.getAdapter().getCount() > 0 && enableInfinityLoop) {
            item = bannerDecorAdapter.getCount() >> 1 + item % bannerDecorAdapter.getRealCount();
        }
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * 获取当前实际的item位置
     *
     * @return
     */
    public int getCurrentRealItem() {
        if (bannerDecorAdapter != null && bannerDecorAdapter.getAdapter().getCount() > 0 && enableInfinityLoop) {
            return super.getCurrentItem() % bannerDecorAdapter.getRealCount();
        } else {
            return super.getCurrentItem();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.touchScrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.touchScrollable && super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置是否可以手动滑动
     *
     * @param scrollable
     */
    public void setTouchScrollable(boolean scrollable) {
        this.touchScrollable = scrollable;
    }
}
