package com.qfxl.view.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author qfxl
 */
public class XViewPagerScroller extends Scroller {
    /**
     * 滑动的时间，默认800ms
     */
    private int scrollDuration = 800;

    public XViewPagerScroller(Context context) {
        super(context);
    }

    public XViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public XViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setScrollDuration(int duration) {
        scrollDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, scrollDuration);
    }
}
