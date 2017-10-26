package com.qfxl.view.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qfxl.view.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qfxl
 */
public class DefaultPagerIndicator extends XViewPagerBaseIndicator {
    /**
     * 指示器集合
     */
    private List<ImageView> indicatorList;
    /**
     * 是否已经绑定了viewPager
     */
    private boolean isAttached;
    /**
     * 正常的资源id
     */
    private int normalResId = R.drawable.shape_default_indicator_normal;
    /**
     * 被选中的资源id
     */
    private int selectResId = R.drawable.shape_default_indicator_select;
    /**
     * 指示器默认的尺寸
     */
    private int indicatorDefaultSize;
    /**
     * 指示器默认的间距
     */
    private int indicatorDefaultMargin;

    public DefaultPagerIndicator(Context context) {
        this(context, null);
    }

    public DefaultPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        indicatorList = new ArrayList<>();
    }

    /**
     * 设置正常的指示器资源id
     *
     * @param resId
     */
    public void setIndicatorNormalResId(int resId) {
        normalResId = resId;
    }

    /**
     * 设置指示器选中的资源id
     *
     * @param resId
     */
    public void setIndicatorSelectResId(int resId) {
        selectResId = resId;
    }

    /**
     * 指示器默认的大小
     * @param defaultSize
     */
    public void setIndicatorDefaultSize(int defaultSize) {
        indicatorDefaultSize = defaultSize;
    }

    /**
     * 设置指示器的外间距
     * @param defaultMargin
     */
    public void setIndicatorDefaultMargin(int defaultMargin) {
        indicatorDefaultMargin = defaultMargin;
    }
    @Override
    protected void onItemSelected(int position) {
        for (int i = 0; i < indicatorList.size(); i++) {
            if (i == position) {
                indicatorList.get(i).setImageResource(selectResId);
            } else {
                indicatorList.get(i).setImageResource(normalResId);
            }
        }
    }

    @Override
    protected void createIndicators(int itemCount) {
        if (isAttached && indicatorList != null) {
            indicatorList.clear();
            removeAllViews();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(indicatorDefaultSize, indicatorDefaultSize);
        lp.setMargins(indicatorDefaultMargin,indicatorDefaultMargin,indicatorDefaultMargin,indicatorDefaultMargin);
        for (int i = 0; i < itemCount; i++) {
            ImageView mImageView = new ImageView(getContext());
            mImageView.setLayoutParams(lp);
            if (i == 0) {
                mImageView.setImageResource(selectResId);
            } else {
                mImageView.setImageResource(normalResId);
            }
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            addView(mImageView);
            indicatorList.add(mImageView);
        }
        isAttached = true;
    }
}
