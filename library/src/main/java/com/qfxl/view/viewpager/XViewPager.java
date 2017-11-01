package com.qfxl.view.viewpager;

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
import android.widget.RelativeLayout;

import com.qfxl.view.R;
import com.qfxl.view.indicator.DefaultPagerIndicator;
import com.qfxl.view.indicator.XViewPagerBaseIndicator;


/**
 * @author qfxl
 *         1，支持自动轮播
 *         2，支持循环轮播
 *         3，支持设置滑动速率
 *         4，支持设置是否可以手动滑动
 *         5，支持设置指示器及自定义指示器
 *         6，其他所有ViewPager的特性
 */
public class XViewPager extends RelativeLayout {
    /**
     * ViewPager
     */
    private XViewPagerView xViewPagerView;
    /**
     * 指示器
     */
    private XViewPagerBaseIndicator mPagerIndicator;
    /**
     * 是否使用默认的指示器，默认true
     */
    private boolean useDefaultIndicator = true;
    /**
     * 指示器的高度
     */
    private int indicatorLayoutWidth;
    /**
     * 指示器的高度
     */
    private int indicatorLayoutHeight;
    /**
     * 指示器的背景色
     */
    private int indicatorBackgroundColor;
    /**
     * 指示器的位置，上中下
     */
    private IndicatorPosition mIndicatorPosition;
    /**
     * 指示器的默认lp
     */
    private LayoutParams mIndicatorLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    /**
     * 默认值指示器选中时的指示器资源id
     */
    private int defaultIndicatorSelectedResId;
    /**
     * 默认值指示器正常情况下的指示器资源id
     */
    private int defaultIndicatorNormalResId;
    /**
     * 设置默认指示器的大小
     */
    private int defaultIndicatorSize;
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
        TOP(0),
        CENTER(1),
        BOTTOM(2);

        IndicatorPosition(int index) {
            nativeInt = index;
        }

        final int nativeInt;
    }

    public XViewPager(Context context) {
        this(context, null);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        xViewPagerView = new XViewPagerView(getContext());
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XViewPager);
        setEnableInfinityLoop(a.getBoolean(R.styleable.XViewPager_XViewPager_enableInfinityLoop, true));
        setLoopInterval(a.getInteger(R.styleable.XViewPager_XViewPager_loopInterval, 3000));
        setAutoLoop(a.getBoolean(R.styleable.XViewPager_XViewPager_autoLoop, false));
        setScrollDuration(a.getInteger(R.styleable.XViewPager_XViewPager_scrollDuration, 800));
        setTouchScrollable(a.getBoolean(R.styleable.XViewPager_XViewPager_touchEnable, true));
        setIndicatorLayoutBackgroundColor(a.getColor(R.styleable.XViewPager_XViewPager_indicator_background_color, getBackgroundColor()));
        setDefaultIndicatorNormalResId(a.getResourceId(R.styleable.XViewPager_XViewPager_default_indicator_normal_resId, R.drawable.shape_default_indicator_normal));
        setDefaultIndicatorSelectResId(a.getResourceId(R.styleable.XViewPager_XViewPager_default_indicator_select_resId, R.drawable.shape_default_indicator_select));
        setDefaultIndicatorMargin(a.getDimensionPixelOffset(R.styleable.XViewPager_XViewPager_default_indicator_margin, (int) sp2px(2)));
        setDefaultIndicatorSize(a.getDimensionPixelOffset(R.styleable.XViewPager_XViewPager_default_indicator_size, (int) sp2px(6)));
        setPageMargin(a.getDimensionPixelOffset(R.styleable.XViewPager_XViewPager_page_margin, 0));
        setOffscreenPageLimit(a.getInteger(R.styleable.XViewPager_XViewPager_page_offscreen_limit,1));
        int defaultGravity = a.getInt(R.styleable.XViewPager_XViewPager_default_indicator_gravity, 1);
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
        setIndicatorPosition(mIndicatorPositions[a.getInt(R.styleable.XViewPager_XViewPager_indicator_position, 2)]);
        a.recycle();
        addView(xViewPagerView, lp);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            xViewPagerView.setClipChildren(getClipChildren());
        }
    }

    /**
     * 设置是否可以无限循环 (默认是开启的)
     *
     * @param enableInfinityLoop
     * @return this
     */
    public XViewPager setEnableInfinityLoop(boolean enableInfinityLoop) {
        if (xViewPagerView != null) {
            xViewPagerView.setEnableInfinityLoop(enableInfinityLoop);
        }
        return this;
    }

    /**
     * 设置是否自动滚动
     *
     * @param autoLoop
     * @return this
     */
    public XViewPager setAutoLoop(boolean autoLoop) {
        if (xViewPagerView != null) {
            xViewPagerView.setAutoLoop(autoLoop);
        }
        return this;
    }

    /**
     * 设置轮播时间
     *
     * @param timeMillis
     * @return this
     */
    public XViewPager setLoopInterval(int timeMillis) {
        if (xViewPagerView != null) {
            xViewPagerView.setLoopInterval(timeMillis);
        }
        return this;
    }

    /**
     * 设置适配器
     *
     * @param pagerAdapter
     * @return this
     */
    public XViewPager setAdapter(PagerAdapter pagerAdapter) {
        if (xViewPagerView != null) {
            xViewPagerView.setAdapter(pagerAdapter);
            //如果使用默认的指示器adapter.getCount()必须>1
            if (useDefaultIndicator && pagerAdapter.getCount() > 1) {
                mPagerIndicator = new DefaultPagerIndicator(getContext());
                //指示器资源设置
                if (defaultIndicatorSelectedResId != 0) {
                    ((DefaultPagerIndicator) mPagerIndicator).setIndicatorSelectResId(defaultIndicatorSelectedResId);
                }
                if (defaultIndicatorNormalResId != 0) {
                    ((DefaultPagerIndicator) mPagerIndicator).setIndicatorNormalResId(defaultIndicatorNormalResId);
                }
                //设置默认指示器item的大小
                if (defaultIndicatorSize > 0) {
                    ((DefaultPagerIndicator) mPagerIndicator).setIndicatorDefaultSize(defaultIndicatorSize);
                }
                //设置默认指示器item的左右边距
                if (defaultIndicatorSize > 0) {
                    ((DefaultPagerIndicator) mPagerIndicator).setIndicatorDefaultMargin(defaultIndicatorMargin);
                }
                //设置默认指示器的gravity，默认垂直居中
                if (defaultIndicatorSize > 0) {
                    mPagerIndicator.setGravity(Gravity.CENTER_VERTICAL | defaultIndicatorGravity);
                }
                setPagerIndicator(mPagerIndicator);
            }
        }
        return this;
    }


    /**
     * 设置是否可以手动滑动
     *
     * @param scrollable
     * @return this
     */
    public XViewPager setTouchScrollable(boolean scrollable) {
        if (xViewPagerView != null) {
            xViewPagerView.setTouchScrollable(scrollable);
        }
        return this;
    }

    /**
     * 设置滑动速率
     *
     * @param duration
     * @return this
     */
    public XViewPager setScrollDuration(int duration) {
        if (xViewPagerView != null) {
            xViewPagerView.setScrollDuration(duration);
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
    public XViewPager setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer pageTransformer) {
        if (xViewPagerView != null) {
            xViewPagerView.setPageTransformer(reverseDrawingOrder, pageTransformer);
        }
        return this;
    }

    /**
     * 设置是否使用默认的指示器
     *
     * @param useDefaultIndicator
     * @return this
     */
    public XViewPager setUseDefaultIndicator(boolean useDefaultIndicator) {
        this.useDefaultIndicator = useDefaultIndicator;
        return this;
    }

    /**
     * 指示器的高度
     *
     * @param height
     * @return this
     */
    public XViewPager setIndicatorLayoutParams(int width, int height) {
        indicatorLayoutWidth = width;
        indicatorLayoutHeight = height;
        return this;
    }

    public XViewPager setIndicatorPosition(IndicatorPosition indicatorPosition) {
        mIndicatorPosition = indicatorPosition;
        return this;
    }

    /**
     * 设置指示器的layoutParams
     *
     * @param layoutParams
     * @return
     */
    public XViewPager setIndicatorLayoutParams(LayoutParams layoutParams) {
        mIndicatorLayoutParams = layoutParams;
        return this;
    }

    /**
     * 设置默认指示器的大小
     *
     * @param size
     * @return
     */
    public XViewPager setDefaultIndicatorSize(int size) {
        defaultIndicatorSize = size;
        return this;
    }

    /**
     * 设置默认指示器的gravity
     *
     * @param gravity
     * @return
     */
    public XViewPager setDefaultIndicatorGravity(int gravity) {
        defaultIndicatorGravity = gravity;
        return this;
    }

    /**
     * 设置默认指示器的大小
     *
     * @param margin
     * @return
     */
    public XViewPager setDefaultIndicatorMargin(int margin) {
        defaultIndicatorMargin = margin;
        return this;
    }

    /**
     * 设置指示器的背景色
     *
     * @param color
     * @return
     */
    public XViewPager setIndicatorLayoutBackgroundColor(int color) {
        indicatorBackgroundColor = color;
        return this;
    }

    /**
     * 设置ViewPager item的外边距
     *
     * @param margin
     * @return
     */
    public XViewPager setPageMargin(int margin) {
        if (xViewPagerView != null) {
            xViewPagerView.setPageMargin(margin);
        }
        return this;
    }

    /**
     * 设置ViewPager 离屏缓存的数量
     * @param limit
     * @return
     */
    public XViewPager setOffscreenPageLimit(int limit) {
        if (xViewPagerView != null) {
            xViewPagerView.setOffscreenPageLimit(limit);
        }
        return this;
    }
    /**
     * 指示器初始化
     */
    private void createIndicators() {
        mPagerIndicator.setViewPager(xViewPagerView);
        LayoutParams indicatorLp;
        //如果宽高有一为0则启用LayoutParams
        if (indicatorLayoutWidth == 0 || indicatorLayoutHeight == 0) {
            indicatorLp = mIndicatorLayoutParams;
        } else {
            indicatorLp = new LayoutParams(indicatorLayoutWidth, indicatorLayoutHeight);
        }
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
     * @param xViewPagerBaseIndicator
     */
    public void setPagerIndicator(XViewPagerBaseIndicator xViewPagerBaseIndicator) {
        if (getViewPager().getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have a adapter");
        }
        if (mPagerIndicator != null) {
            removeView(mPagerIndicator);
        }
        if (xViewPagerBaseIndicator != null) {
            mPagerIndicator = xViewPagerBaseIndicator;
        }
        //设置背景色
        if (mPagerIndicator != null && indicatorBackgroundColor != 0) {
            mPagerIndicator.setBackgroundColor(indicatorBackgroundColor);
        }
        createIndicators();
    }

    /**
     * 设置正常的指示器资源id
     *
     * @return this
     */
    public XViewPager setDefaultIndicatorNormalResId(int resId) {
        defaultIndicatorNormalResId = resId;
        return this;
    }

    /**
     * 设置被选中的指示器资源id
     *
     * @return this
     */
    public XViewPager setDefaultIndicatorSelectResId(int resId) {
        defaultIndicatorSelectedResId = resId;
        return this;
    }

    /**
     * 获取当前ViewPager的实例
     *
     * @return
     */
    public XViewPagerView getViewPager() {
        return xViewPagerView;
    }

    /**
     * 获取指示器
     *
     * @return
     */
    public XViewPagerBaseIndicator getPagerIndicator() {
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
     * sp2px
     *
     * @param inParam
     * @return
     */
    private float sp2px(float inParam) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, inParam, getContext().getResources().getDisplayMetrics());
    }
}
