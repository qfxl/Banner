package com.qfxl.samples.indicators;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.qfxl.samples.Utils;
import com.qfxl.view.indicator.BaseIndicator;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

public class PagerIndicatorViewIndicator extends BaseIndicator {
    private PageIndicatorView pageIndicatorView;
    private AnimationType animationType = AnimationType.DROP;

    public PagerIndicatorViewIndicator(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    public PagerIndicatorViewIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER);
    }

    public PagerIndicatorViewIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

    @Override
    protected void onItemSelected(int position) {
        pageIndicatorView.setSelection(position);
    }

    @Override
    protected void onItemScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pageIndicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    protected void createIndicators(int itemCount) {
        pageIndicatorView = new PageIndicatorView(getContext());
        int height = animationType == AnimationType.DROP? Utils.dp2px(getContext(), 16) : Utils.dp2px(getContext(), 10);
        pageIndicatorView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, height));
        pageIndicatorView.setDynamicCount(true);
        pageIndicatorView.setSelected(Color.RED);
        pageIndicatorView.setUnselectedColor(Color.GRAY);
        pageIndicatorView.setInteractiveAnimation(true);
        pageIndicatorView.setRadius(3);
        pageIndicatorView.setAnimationType(animationType);
        addView(pageIndicatorView);
    }
}
