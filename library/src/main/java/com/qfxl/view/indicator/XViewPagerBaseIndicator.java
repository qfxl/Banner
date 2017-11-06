package com.qfxl.view.indicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.qfxl.view.viewpager.XViewPagerView;


/**
 * @author qfxl
 */
public abstract class XViewPagerBaseIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    /**
     * 关联的LoopViewPager
     */
    protected XViewPagerView mXViewPagerView;
    /**
     * item的总个数
     */
    protected int itemTotalCount;
    /**
     * ViewPager item选中监听
     */
    private OnPageSelectListener mOnPageSelectListener;

    public interface OnPageSelectListener {
        /**
         * ViewPager item选中监听
         *
         * @param position
         */
        void onItemSelected(int position);
    }


    public XViewPagerBaseIndicator(Context context) {
        this(context, null);
    }

    public XViewPagerBaseIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XViewPagerBaseIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置ViewPager的滑动监听
     *
     * @param onPageSelectListener
     */
    public void setOnPageSelectListener(OnPageSelectListener onPageSelectListener) {
        mOnPageSelectListener = onPageSelectListener;
    }

    /**
     * 关联LoopViewPager
     *
     * @param xViewPagerView
     */
    public void setViewPager(XViewPagerView xViewPagerView) {
        if (xViewPagerView == mXViewPagerView) {
            return;
        }
        if (xViewPagerView != null && xViewPagerView.getAdapter() != null) {
            mXViewPagerView = xViewPagerView;
            mXViewPagerView.addOnPageChangeListener(this);
            itemTotalCount = mXViewPagerView.getXViewPagerAdapter().getRealCount();
            //只有数量>1才允许设置指示器
            if (itemTotalCount > 1) {
                createIndicators(itemTotalCount);
            }
        }
    }

    /**
     * 获取item的总个数
     *
     * @return
     */
    public int getItemTotalCount() {
        return itemTotalCount;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        onItemScrolled(position % mXViewPagerView.getXViewPagerAdapter().getRealCount(), positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (mOnPageSelectListener != null) {
            mOnPageSelectListener.onItemSelected(mXViewPagerView.getCurrentRealItem());
        }
        onItemSelected(mXViewPagerView.getCurrentRealItem());
    }

    /**
     * 选中item
     *
     * @param position 当前被选中item的位置
     */
    protected abstract void onItemSelected(int position);

    /**
     * 监听item滑动的距离
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    protected void onItemScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 创建指示器
     *
     * @param itemCount item个数
     */
    protected abstract void createIndicators(int itemCount);


    @Override
    public void onPageScrollStateChanged(int state) {
        /**
         * 手指拖动ViewPager的时候停止滚动
         */
        if (mXViewPagerView != null && mXViewPagerView.isAutoLoop()) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                mXViewPagerView.stopLoop();
            } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                mXViewPagerView.startLoop();
            }
        }
    }
}
