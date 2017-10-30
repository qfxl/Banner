package com.qfxl.samples.indicators;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.qfxl.view.indicator.XViewPagerBaseIndicator;



public class LineIndicator extends XViewPagerBaseIndicator {
    private LineIndicatorView indicatorView;

    public LineIndicator(Context context) {
        this(context, null);
    }

    public LineIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onItemSelected(int position) {
        indicatorView.onPageSelected(position);
    }

    @Override
    public void onItemScrolled(int position, float positionOffset, int positionOffsetPixels) {
        indicatorView.setOffsetX(position, positionOffset);
    }

    @Override
    protected void createIndicators(int itemCount) {
        indicatorView = new LineIndicatorView(getContext());
        indicatorView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 20));
        indicatorView.setItemCount(itemCount);
        indicatorView.setItemWidth(60);
        indicatorView.setItemSpace(20);
        addView(indicatorView);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }
}
