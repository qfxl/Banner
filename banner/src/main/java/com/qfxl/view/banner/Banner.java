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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.qfxl.view.R;
import com.qfxl.view.indicator.BaseIndicator;
import com.qfxl.view.indicator.DefaultIndicator;

import java.util.List;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : Banner
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
     * 默认指示器的gravity
     */
    private int indicatorGravity;
    /**
     * 指示器item宽
     */
    private int indicatorItemWidth;
    /**
     * 指示器item高
     */
    private int indicatorItemHeight;
    /**
     * 指示器item的margin
     */
    private int indicatorItemMargin;

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

    /**
     * 默认Banner适配器的图片路径集合
     */
    private List<?> pathList;
    /**
     * 默认Banner适配器的图片加载器
     */
    private IBannerImageLoader imageLoader;
    /**
     * 默认Banner点击监听
     */
    private BannerDefaultAdapter.OnBannerClickListener onBannerClickListener;

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
        setInfinityLoop(a.getBoolean(R.styleable.Banner_banner_infinityLoop, true));
        setLoopInterval(a.getInteger(R.styleable.Banner_banner_loopInterval, 3000));
        setAutoLoop(a.getBoolean(R.styleable.Banner_banner_autoLoop, true));
        setScrollDuration(a.getInteger(R.styleable.Banner_banner_scrollDuration, 600));
        setTouchScrollable(a.getBoolean(R.styleable.Banner_banner_touchEnable, true));
        setIsDefaultIndicator(a.getBoolean(R.styleable.Banner_banner_isDefaultIndicator, true));
        setIndicatorLayoutBackgroundColor(a.getColor(R.styleable.Banner_banner_indicatorBackgroundColor, getBackgroundColor()));
        setIndicatorNormalResId(a.getResourceId(R.styleable.Banner_banner_indicatorNormalResId, R.drawable.shape_default_indicator_normal));
        setIndicatorSelectResId(a.getResourceId(R.styleable.Banner_banner_indicatorSelectResId, R.drawable.shape_default_indicator_select));
        setIndicatorItemMargin(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemMargin, (int) dp2px(2)));
        setIndicatorWidth(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorWidth, 0));
        setIndicatorHeight(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorHeight, 0));
        setIndicatorItemWidth(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemWidth, (int) dp2px(6)));
        setIndicatorItemHeight(a.getDimensionPixelOffset(R.styleable.Banner_banner_indicatorItemHeight, (int) dp2px(6)));
        setPageMargin(a.getDimensionPixelOffset(R.styleable.Banner_banner_pageMargin, 0));
        setOffscreenPageLimit(a.getInteger(R.styleable.Banner_banner_pageOffscreenLimit, 1));
        int defaultGravity = a.getInt(R.styleable.Banner_banner_indicatorGravity, 1);
        switch (defaultGravity) {
            case 0:
                setIndicatorGravity(Gravity.LEFT);
                break;
            case 1:
                setIndicatorGravity(Gravity.CENTER);
                break;
            case 2:
                setIndicatorGravity(Gravity.RIGHT);
                break;
            default:
                setIndicatorGravity(Gravity.CENTER);
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
        bannerView.setEnableInfinityLoop(enableInfinityLoop);
        return this;
    }

    /**
     * 设置是否自动滚动
     *
     * @param autoLoop
     * @return this
     */
    public Banner setAutoLoop(boolean autoLoop) {
        bannerView.setAutoLoop(autoLoop);
        return this;
    }

    /**
     * 设置轮播时间
     *
     * @param timeMillis
     * @return this
     */
    public Banner setLoopInterval(int timeMillis) {
        bannerView.setLoopInterval(timeMillis);
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
        bannerView.setAdapter(pagerAdapter);
        //如果使用默认的指示器adapter.getCount()必须>1
        if (isDefaultIndicator) {
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
            mPagerIndicator.setGravity(Gravity.CENTER_VERTICAL | indicatorGravity);
            //设置默认指示器item的左右边距
            ((DefaultIndicator) mPagerIndicator).setIndicatorItemMargin(indicatorItemMargin);
            setPagerIndicator(mPagerIndicator);
        }
        if (pagerAdapter.getCount() > 1) {
            createIndicators();
        }
        return this;
    }

    /**
     * 设置是否可以手动滑动
     *
     * @param scrollable
     * @return this
     */
    public Banner setTouchScrollable(boolean scrollable) {
        bannerView.setTouchScrollable(scrollable);
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
        bannerView.setPageTransformer(reverseDrawingOrder, pageTransformer);
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
    public Banner setIndicatorGravity(int gravity) {
        indicatorGravity = gravity;
        return this;
    }

    /**
     * 设置默认指示器的间距
     *
     * @param margin
     * @return
     */
    public Banner setIndicatorItemMargin(int margin) {
        indicatorItemMargin = margin;
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
        bannerView.setPageMargin(margin);
        return this;
    }

    /**
     * 设置ViewPager 离屏缓存的数量
     *
     * @param limit
     * @return
     */
    public Banner setOffscreenPageLimit(int limit) {
        bannerView.setOffscreenPageLimit(limit);
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
        if (mPagerIndicator != null) {
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
    }

    /**
     * 设置指示器
     *
     * @param baseIndicator
     */
    public Banner setPagerIndicator(BaseIndicator baseIndicator) {
        if (mPagerIndicator != null) {
            removeView(mPagerIndicator);
        }
        if (baseIndicator != null) {
            mPagerIndicator = baseIndicator;
            //设置背景色
            if (indicatorBackgroundColor != 0) {
                mPagerIndicator.setBackgroundColor(indicatorBackgroundColor);
            }
        }
        return this;
    }

    /**
     * 设置其他Indicator
     *
     * @param indicatorView
     */
    public Banner setPagerIndicator(View indicatorView) {
        addView(indicatorView);
        return this;
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
    public Banner setIndicatorSelectResId(int resId) {
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

    public Banner setImageResources(List<?> pathList) {
        this.pathList = pathList;
        return this;
    }

    public Banner setImageLoader(IBannerImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setBannerCLickListener(BannerDefaultAdapter.OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
        return this;
    }

    /**
     * 所有设置完毕。
     */
    public void ready() {
        if (pathList == null) {
            throw new RuntimeException("请在ready()之前调用setImageResources()");
        }
        if (imageLoader == null) {
            throw new RuntimeException("请在ready()之前调用setImageLoader()");
        }
        BannerDefaultAdapter defaultAdapter = new BannerDefaultAdapter(pathList);
        defaultAdapter.setBannerImageLoader(imageLoader);
        defaultAdapter.setBannerClickListener(onBannerClickListener);
        setAdapter(defaultAdapter);
    }

    /**
     * 设置数据源
     *
     * @param pathList
     */
    public void autoReady(List<?> pathList, IBannerImageLoader imageLoader, BannerDefaultAdapter.OnBannerClickListener onBannerClickListener) {
        BannerDefaultAdapter defaultAdapter = new BannerDefaultAdapter(pathList);
        defaultAdapter.setBannerImageLoader(imageLoader);
        defaultAdapter.setBannerClickListener(onBannerClickListener);
        setAdapter(defaultAdapter);
    }

    /**
     * 是否调用过ready()
     * @return
     */
    public boolean isReady() {
        return bannerView.getAdapter() != null;
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
