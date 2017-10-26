package com.qfxl.samples.indicators;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qfxl.samples.R;
import com.qfxl.view.indicator.XViewPagerBaseIndicator;


/**
 * @author qfxl
 */
public class TextPagerIndicator extends XViewPagerBaseIndicator {

    private TextView textView;

    public TextPagerIndicator(Context context) {
        this(context, null);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.RIGHT);
        setPadding(0, 0, 10, 0);
    }

    @Override
    protected void onItemSelected(int position) {
        textView.setText(position + 1 + "/" + getItemTotalCount());
    }

    @Override
    protected void createIndicators(int itemCount) {
        textView = new TextView(getContext());
        textView.setTextSize(16);
        textView.setPadding(15, 15, 15, 15);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.drawable.shape_text_indicator);
        textView.setText("1/" + itemCount);
        addView(textView);
    }
}
