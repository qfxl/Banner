package com.qfxl.samples.adapter;

import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qfxl.samples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称 :
 * 作   者 : xyh
 * 创建时间 : 2017/10/24 13:53
 * 文件描述 :
 * 版权声明 : Copyright (C) 2015-2018 杭州中焯信息技术股份有限公司
 * 修改历史 : 2017/10/24 1.00 初始版本
 * ****************************************************************
 */


public class ShortcutPagerAdapter extends BasePagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        List<Pair<String, Integer>> itemList = new ArrayList<>();
        GridView gridView = new GridView(container.getContext());
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setVerticalSpacing(30);
        gridView.setNumColumns(4);
        for (int j = 0; j < 8; j++) {
            Pair<String, Integer> pair = Pair.create("page" + position + "-item" + j, R.mipmap.ic_launcher);
            itemList.add(pair);
        }
        gridView.setAdapter(new GridViewAdapter(itemList));
        container.addView(gridView);
        return gridView;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
