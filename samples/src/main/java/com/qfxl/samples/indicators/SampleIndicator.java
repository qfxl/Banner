package com.qfxl.samples.indicators;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qfxl.samples.R;
import com.qfxl.samples.Utils;
import com.qfxl.view.indicator.BaseIndicator;

import java.util.ArrayList;
import java.util.List;

public class SampleIndicator extends BaseIndicator {
    /**
     * 指示器集合
     */
    private List<ImageView> indicatorList;
    /**
     * 是否已经绑定了viewPager
     */
    private boolean isAttached;
    /**
     * 指示器默认的间距
     */
    private int indicatorItemMargin;

    private int resId = R.drawable.sp_line_indicator_normal;

    private LayoutParams normalLp;
    private LayoutParams selectedLp;

    public SampleIndicator(Context context) {
        this(context, null);
    }

    public SampleIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        indicatorList = new ArrayList<>();
        setGravity(Gravity.CENTER);
        normalLp = new LayoutParams(Utils.dp2px(getContext(), 14), Utils.dp2px(getContext(), 1));
        normalLp.setMargins(Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3));
        selectedLp = new LayoutParams(Utils.dp2px(getContext(), 14), Utils.dp2px(getContext(), 2));
        selectedLp.setMargins(Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3), Utils.dp2px(getContext(), 3));
    }

    @Override
    protected void onItemSelected(int position) {
        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == position) {
                indicatorList.get(i).setLayoutParams(selectedLp);
            } else {
                indicatorList.get(i).setLayoutParams(normalLp);
            }
        }
    }

    @Override
    protected void createIndicators(int itemCount) {
        if (isAttached && indicatorList != null) {
            indicatorList.clear();
            removeAllViews();
        }
        for (int i = 0; i < itemCount; i++) {
            ImageView mImageView = new ImageView(getContext());
            mImageView.setImageResource(resId);
            if (i == 0) {
                mImageView.setLayoutParams(selectedLp);
            } else {
                mImageView.setLayoutParams(normalLp);
            }
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(mImageView);
            indicatorList.add(mImageView);
        }
        isAttached = true;
    }
}
