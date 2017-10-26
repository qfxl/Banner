package com.qfxl.samples.indicators;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qfxl.samples.R;
import com.qfxl.view.indicator.XViewPagerBaseIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称 :
 * 作   者 : xyh
 * 创建时间 : 2017/10/24 15:14
 * 文件描述 :
 * 版权声明 : Copyright (C) 2015-2018 杭州中焯信息技术股份有限公司
 * 修改历史 : 2017/10/24 1.00 初始版本
 * ****************************************************************
 */


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
}
