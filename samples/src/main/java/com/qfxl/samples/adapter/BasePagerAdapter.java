package com.qfxl.samples.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * ****************************************************************
 * 文件名称 :
 * 作   者 : xyh
 * 创建时间 : 2017/10/24 13:50
 * 文件描述 :
 * 版权声明 : Copyright (C) 2015-2018 杭州中焯信息技术股份有限公司
 * 修改历史 : 2017/10/24 1.00 初始版本
 * ****************************************************************
 */


public abstract class BasePagerAdapter extends PagerAdapter {

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
