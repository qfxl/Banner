package com.qfxl.samples.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qfxl.samples.R;

/**
 * ****************************************************************
 * 文件名称 :
 * 作   者 : xyh
 * 创建时间 : 2017/10/24 11:02
 * 文件描述 :
 * 版权声明 : Copyright (C) 2015-2018 杭州中焯信息技术股份有限公司
 * 修改历史 : 2017/10/24 1.00 初始版本
 * ****************************************************************
 */


public class BannerAdapter extends BasePagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView = new ImageView(container.getContext());
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        switch (position) {
            case 0:
                mImageView.setImageResource(R.drawable.banner_1);
                break;
            case 1:
                mImageView.setImageResource(R.drawable.banner_2);
                break;
            case 2:
                mImageView.setImageResource(R.drawable.banner_3);
                break;
            default:
        }
        container.addView(mImageView);
        return mImageView;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
