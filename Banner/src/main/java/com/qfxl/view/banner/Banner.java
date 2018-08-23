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
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.qfxl.view.R;
import com.qfxl.view.indicator.DefaultIndicator;
import com.qfxl.view.indicator.BaseIndicator;

import java.util.List;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : XViewPager
 * 1，支持自动轮播
 * 2，支持循环轮播
 * 3，支持设置滑动速率
 * 4，支持设置是否可以手动滑动
 * 5，支持设置指示器及自定义指示器
 * 6，其他所有ViewPager的特性
 *     version: 1.0
 * </pre>
 */

public class Banner extends RelativeLayout {
    /**
     * ViewPager
     */
    private BannerView bannerView;
    /**
     * 指示器
     */
    private BaseIndicator mPagerIndicator;
    /**
     * 是否使用默认的指示器，默认true
     */
    private boolean isDefaultIndicator;
    /**
     * 指示器的背景色
     */
    private int indicatorBackgroundColor;
    /**
     * 指示器的位置，上中下
     */
    private IndicatorPosition mIndicatorPosition;
    /**
     * 默认值指示器选中时的指示器资源id
     */
    private int indicatorSelectedResId;
    /**
     * 默认值指示器正常情况下的指示器资源id
     */
    private int indicatorNormalResId;
    /**
     * 指示器宽度
     */
    private int indicatorWidth;
    /**
     * 指示器高度
     */
    private int indicatorHeight;
    /**
     * 指示器item宽
     */
    private int indicatorItemWidth;
    /**
     * 指示器item高
     */
    private int indicatorItemHeight;
    /**
     * 默认指示器的margin
     */
    private int defaultIndicatorMargin;
    /**
     * 默认指示器的gravity
     */
    private int defaultIndicatorGravity;
    /**
     * 指示器的位置集合，用于获取xml定义的枚举
     */
    private IndicatorPosition[] mIndicatorPositions = {
            IndicatorPosition.TOP,
            IndicatorPosition.CENTER,
            IndicatorPosition.BOTTOM
    };

    /**
     * indicator的位置，上中下
     */
    public enum IndicatorPosition {
        /**
         * 上
         */
        TOP(0),
        /**
         * 中
         */
        CENTER(1),
        /**
         * 下
         */
        BOTTOM(2);

        IndicatorPosition(int index) {
            nativeInt = index;
        }

        final int nativeInt;
    }

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        bannerView = new BannerView(getContext());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        setInfinityLoop(a.getBoolean(R.styleable.Banner_banner_infinityLoop, false));
        setLoopInterval(a.getInteger(R.styleable.Banner_banner_loopInterval, 3000));
        setAutoStart(a.getBoolean(R.styleable.Banner_banner_autoStart, false));
        setScrollDuration(a.getInteger(R.styleable.Banner_banner_scrollDuration, 600));
        setTouchScrollable(a.getBoolean(R.styleable.Banner_banner_touchEnable, true));
        setIsDefaultIndicator(a.getBoolean(R.styleable.Banner_banner_isDefaultIndicator, true));
        setIndicatorLayoutBackgroundColor(a.getColor(R.styleable.Banner_banner_indicatorBackgroundColor, getBackgroundColor()));
        setIndicatorNormalResId(a.getResourceId(R.styleable.Banner_banner_indicatorNormalResId, R.drawable.shape_default_indicator_normal));
        setDefaultIndicatorSelectResId(a.getResourceId(R.styleable.Banner_banner_indicatorSelectResId, R.drawable.shape_default_indicator_select));
        setDefaultIndicatorMargin(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorMargin, (int) dp2px(2)));
        setIndicatorWidth(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorWidth, 0));
        setIndicatorHeight(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorHeight, 0));
        setIndicatorItemWidth(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemWidth, (int) dp2px(6)));
        setIndicatorItemHeight(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemHeight, (int) dp2px(6)));
        setPageMargin(a.getDimensionPixelOffset(R.styleable.Banner_banner_pageMargin, 0));
        setOffscreenPageLimit(a.getInteger(R.styleable.Banner_banner_pageOffscreenLimit, 1));
        int defaultGravity = a.getInt(R.styleable.Banner_banner_indicatorGravity, 1);
        switch (defaultGravity) {
            case 0:
                setDefaultIndicatorGravity(Gravity.LEFT);
                break;
            case 1:
                setDefaultIndicatorGravity(Gravity.CENTER);
                break;
            case 2:
                setDefaultIndicatorGravity(Gravity.RIGHT);
                break;
            default:
                setDefaultIndicatorGravity(Gravity.CENTER);
        }
        setIndicatorPosition(mIndicatorPositions[a.getInt(R.styleable.Banner_banner_indicatorPosition, 2)]);
        a.recycle();
        addView(bannerView, lp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bannerView.setClipChildren(getClipChildren());
        }
    }

    /**
     * 设置是否可以无限循环 (默认是开启的)
     *
     * @param enableInfinityLoop
     * @return this
     */
    public Banner setInfinityLoop(boolean enableInfinityLoop) {
        if (bannerView != null) {
            bannerView.setEnableInfinityLoop(enableInfinityLoop);
        }
        return this;
    }

    /**
     * 设置是否自动滚动
     *
     * @param autoStart
     * @return this
     */
    public Banner setAutoStart(boolean autoStart) {
        if (bannerView != null) {
            bannerView.setAutoStart(autoStart);
        }
        return this;
    }

    /**
     * 设置轮播时间
     *
     * @param timeMillis
     * @return this
     */
    public Banner setLoopInterval(int timeMillis) {
        if (bannerView != null) {
            bannerView.setLoopInterval(timeMillis);
        }
        return this;
    }

    /**
     * 设置适配器
     *
     * @param pagerAdapter
     * @return this
     */
    public Banner setAdapter(PagerAdapter pagerAdapter) {
        if (mPagerIndicator != null) {
            removeView(mPagerIndicator);
        }
        if (bannerView != null) {
            bannerView.setAdapter(pagerAdapter);
            //如果使用默认的指示器adapter.getCount()必须>1
            if (isDefaultIndicator && pagerAdapter.getCount() > 1) {
                mPagerIndicator = new DefaultIndicator(getContext());
                //指示器资源设置
                if (indicatorSelectedResId != 0) {
                    ((DefaultIndicator) mPagerIndicator).setIndicatorSelectResId(indicatorSelectedResId);
                }
                if (indicatorNormalResId != 0) {
                    ((DefaultIndicator) mPagerIndicator).setIndicatorNormalResId(indicatorNormalResId);
                }
                //设置默认指示器item的大小
                ((DefaultIndicator) mPagerIndicator).setIndicatorItemWidth(indicatorItemWidth);
                ((DefaultIndicator) mPagerIndicator).setIndicatorItemHeight(indicatorItemHeight);
                mPagerIndicator.setGravity(Gravity.CENTER_VERTICAL | defaultIndicatorGravity);
                //设置默认指示器item的左右边距
                ((DefaultIndicator) mPagerIndicator).setIndicatorDefaultMargin(defaultIndicatorMargin);
                setPagerIndicator(mPagerIndicator);
            }
        }
        return this;
    }

    /**
     * 设置pathList
     *
     * @param pathList
     */
    public void setImagePathList(List<?> pathList, IBannerImageLoader imageLoader, BannerDefaultAdapter.OnBannerClickListener onBannerClickListener) {
        BannerDefaultAdapter defaultAdapter = new BannerDefaultAdapter(pathList);
        defaultAdapter.setBannerImageLoader(imageLoader);
        defaultAdapter.setBannerClickListener(onBannerClickListener);
        setAdapter(defaultAdapter);
    }

    /**
     * 设置是否可以手动滑动
     *
     * @param scrollable
     * @return this
     */
    public Banner setTouchScrollable(boolean scrollable) {
        if (bannerView != null) {
            bannerView.setTouchScrollable(scrollable);
        }
        return this;
    }

    /**
     * 设置滑动速率
     *
     * @param duration
     * @return this
     */
    public Banner setScrollDuration(int duration) {
        if (bannerView != null) {
            bannerView.setScrollDuration(duration);
        }
        return this;
    }

    /**
     * 设置ViewPager的切换动画
     *
     * @param reverseDrawingOrder
     * @param pageTransformer
     * @return
     */
    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer pageTransformer) {
        if (bannerView != null) {
            bannerView.setPageTransformer(reverseDrawingOrder, pageTransformer);
        }
        return this;
    }

    /**
     * 设置是否使用默认的指示器
     *
     * @param defaultIndicator
     * @return this
     */
    public Banner setIsDefaultIndicator(boolean defaultIndicator) {
        this.isDefaultIndicator = defaultIndicator;
        return this;
    }

    public Banner setIndicatorPosition(IndicatorPosition indicatorPosition) {
        mIndicatorPosition = indicatorPosition;
        return this;
    }

    /**
     * 设置默认指示器的gravity
     *
     * @param gravity
     * @return
     */
    public Banner setDefaultIndicatorGravity(int gravity) {
        defaultIndicatorGravity = gravity;
        return this;
    }

    /**
     * 设置默认指示器的大小
     *
     * @param margin
     * @return
     */
    public Banner setDefaultIndicatorMargin(int margin) {
        defaultIndicatorMargin = margin;
        return this;
    }

    /**
     * 设置指示器的背景色
     *
     * @param color
     * @return
     */
    public Banner setIndicatorLayoutBackgroundColor(int color) {
        indicatorBackgroundColor = color;
        return this;
    }

    /**
     * 设置ViewPager item的外边距
     *
     * @param margin
     * @return
     */
    public Banner setPageMargin(int margin) {
        if (bannerView != null) {
            bannerView.setPageMargin(margin);
        }
        return this;
    }

    /**
     * 设置ViewPager 离屏缓存的数量
     *
     * @param limit
     * @return
     */
    public Banner setOffscreenPageLimit(int limit) {
        if (bannerView != null) {
            bannerView.setOffscreenPageLimit(limit);
        }
        return this;
    }

    public Banner setIndicatorWidth(int indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
        return this;
    }

    public Banner setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        return this;
    }

    public Banner setIndicatorItemWidth(int indicatorItemWidth) {
        this.indicatorItemWidth = indicatorItemWidth;
        return this;
    }

    public Banner setIndicatorItemHeight(int indicatorItemHeight) {
        this.indicatorItemHeight = indicatorItemHeight;
        return this;
    }

    /**
     * 指示器初始化
     */
    private void createIndicators() {
        mPagerIndicator.setViewPager(bannerView);
        LayoutParams indicatorLp;
        //如果宽高有一为0则使用默认LayoutParams
        indicatorLp = new LayoutParams(indicatorWidth == 0 ? LayoutParams.MATCH_PARENT : indicatorWidth,
                indicatorHeight == 0 ? LayoutParams.WRAP_CONTENT : indicatorHeight);
        //设置indicator的位置
        switch (mIndicatorPosition) {
            case TOP:
                indicatorLp.addRule(ALIGN_PARENT_TOP);
                break;
            case CENTER:
                indicatorLp.addRule(CENTER_IN_PARENT);
                break;
            case BOTTOM:
                indicatorLp.addRule(ALIGN_PARENT_BOTTOM);
                break;
            default:
                indicatorLp.addRule(ALIGN_PARENT_BOTTOM);
        }
        mPagerIndicator.setLayoutParams(indicatorLp);
        addView(mPagerIndicator);
    }

    /**
     * 设置指示器
     *
     * @param baseIndicator
     */
    public void setPagerIndicator(BaseIndicator baseIndicator) {
        if (getViewPager().getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have a adapter");
        }
        if (mPagerIndicator != null) {
            removeView(mPagerIndicator);
        }
        if (baseIndicator != null) {
            mPagerIndicator = baseIndicator;
        }
        //设置背景色
        if (mPagerIndicator != null && indicatorBackgroundColor != 0) {
            mPagerIndicator.setBackgroundColor(indicatorBackgroundColor);
        }
        createIndicators();
    }

    /**
     * 设置其他Indicator
     *
     * @param indicatorView
     */
    public void setPagerIndicator(View indicatorView) {
        addView(indicatorView);
    }

    /**
     * 设置正常的指示器资源id
     *
     * @return this
     */
    public Banner setIndicatorNormalResId(int resId) {
        indicatorNormalResId = resId;
        return this;
    }

    /**
     * 设置被选中的指示器资源id
     *
     * @return this
     */
    public Banner setDefaultIndicatorSelectResId(int resId) {
        indicatorSelectedResId = resId;
        return this;
    }

    /**
     * 获取当前ViewPager的实例
     *
     * @return
     */
    public BannerView getViewPager() {
        return bannerView;
    }

    /**
     * 获取指示器
     *
     * @return
     */
    public BaseIndicator getPagerIndicator() {
        return mPagerIndicator;
    }

    /**
     * 获取背景色
     *
     * @return
     */
    private int getBackgroundColor() {
        Drawable drawable = getBackground();
        if (drawable != null && drawable instanceof ColorDrawable) {
            return ((ColorDrawable) drawable).getColor();
        } else {
            return 0;
        }
    }

    /**
     * dp2px
     *
     * @param inParam
     * @return
     */
    private float dp2px(float inParam) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, inParam, getContext().getResources().getDisplayMetrics());
    }
}
