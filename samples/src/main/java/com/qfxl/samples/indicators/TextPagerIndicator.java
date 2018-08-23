package com.qfxl.samples.indicators;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.qfxl.samples.R;
import com.qfxl.view.indicator.BaseIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qfxl
 */
public class TextPagerIndicator extends BaseIndicator {

    private TextView textView;

    private List<String> indicatorTextList;

    public TextPagerIndicator(Context context) {
        this(context, null);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        indicatorTextList = new ArrayList<>();
        indicatorTextList.add("连战争模式都不敢开，还敢说爱她？");
        indicatorTextList.add("细读经典：宫崎骏的动画巅峰之作《幽灵公主》。");
        indicatorTextList.add("copy忍者-卡卡西。");
        setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        setPadding(0, 0, 10, 0);
        setBackgroundColor(0x40efefef);

    }

    @Override
    protected void onItemSelected(int position) {
        textView.setText(indicatorTextList.get(position));
    }

    @Override
    protected void createIndicators(int itemCount) {
        textView = new TextView(getContext());
        textView.setTextSize(14);
        textView.setPadding(15, 15, 15, 15);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.shape_text_indicator);
        textView.setText(indicatorTextList.get(0));
        addView(textView);
    }
}
