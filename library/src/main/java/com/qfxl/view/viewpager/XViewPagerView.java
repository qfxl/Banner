package com.qfxl.view.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Field;

/**
 * @author qfxl
 */
public class XViewPagerView extends ViewPager implements XViewPagerAdapter.LoopViewPagerCenterListener {
    /**
     * 是否可以循环(默认可以)
     */
    private boolean enableLoop;
    /**
     * 自动循环的间隔(默认3s)
     */
    private int loopInterval;
    /**
     * 设置循环时使用的适配器
     */
    private XViewPagerAdapter xViewPagerAdapter;
    /**
     * 是否自动滚动(默认不自动)
     */
    private boolean isAutoLoop;
    /**
     * viewPager是否可以手动滑动(默认可以滚动)
     */
    private boolean touchScrollable;

    public XViewPagerView(Context context) {
        super(context);
    }

    public XViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

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
        if (adapter != null) {
            if (xViewPagerAdapter == null || xViewPagerAdapter.getAdapter() != adapter) {
                xViewPagerAdapter = new XViewPagerAdapter(adapter);
                xViewPagerAdapter.setEnableLoop(enableLoop);
                xViewPagerAdapter.setLoopViewPagerCenterListener(this);
                super.setAdapter(xViewPagerAdapter);
            }
        }
    }

    /**
     * 设置是否可以循环
     *
     * @param enableLoop
     */
    public void setEnableLoop(boolean enableLoop) {
        //如果已经设置了适配器，则不允许设置此参数
        if (getAdapter() == null) {
            this.enableLoop = enableLoop;
        } else {
            Log.i(XViewPagerView.class.getName(), "请在setAdapter之前调用setEnableLoop");
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
            XViewPagerScroller scroller = new XViewPagerScroller(getContext());
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
    public XViewPagerAdapter getXViewPagerAdapter() {
        return xViewPagerAdapter;
    }

    /**
     * 显示下一个item
     */
    private void next() {
        if (xViewPagerAdapter != null && xViewPagerAdapter.getCount() > 0) {
            int curr = super.getCurrentItem();
            int nextPage = 0;
            if (curr < xViewPagerAdapter.getCount() - 1) {
                nextPage = curr + 1;
            }
            super.setCurrentItem(nextPage, true);
        }
    }

    @Override
    public void center() {
        if (xViewPagerAdapter != null) {
            setCurrentItem(0);
        }
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (xViewPagerAdapter != null && xViewPagerAdapter.getAdapter().getCount() > 0 && enableLoop) {
            item = xViewPagerAdapter.getCount() >> 1 + item % xViewPagerAdapter.getRealCount();
        }
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * 获取当前实际的item位置
     *
     * @return
     */
    public int getCurrentRealItem() {
        if (xViewPagerAdapter != null && xViewPagerAdapter.getAdapter().getCount() > 0 && enableLoop) {
            return super.getCurrentItem() % xViewPagerAdapter.getRealCount();
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
