package com.qfxl.samples.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author qfxl
 */
public class LineIndicatorView extends View {
    /**
     * 每个item的宽度
     */
    private int itemWidth;
    /**
     * 每个item的间距
     */
    private int itemSpace;
    /**
     * item的个数
     */
    private int itemCount;
    /**
     * 绘制指示器的画笔
     */
    private Paint mPaint;
    /**
     * 滑动的值
     */
    private int offsetX;

    public LineIndicatorView(Context context) {
        this(context, null);
    }

    public LineIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GRAY);
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(), MeasureSpec.getSize(heightMeasureSpec));
    }

    private int measureWidth() {
        return itemWidth * itemCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < itemCount; i++) {
            mPaint.setColor(Color.GRAY);
            int left = i * itemWidth;
            int right = left + itemWidth;
            canvas.drawLine(left + itemSpace, 0, right, 0, mPaint);
        }
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.translate(offsetX, 0);
        canvas.drawLine(itemSpace, 0, itemWidth, 0, mPaint);
        canvas.restore();
    }


    public void setOffsetX(int position, float positionOffset) {
        offsetX = (int) (itemWidth * position + itemWidth * positionOffset);
        invalidate();
    }

    public void onPageSelected(int position) {
        offsetX = itemWidth * position;
        invalidate();
    }
}
