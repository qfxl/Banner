package com.qfxl.samples.adapter;

import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfxl.samples.R;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称 :
 * 作   者 : xyh
 * 创建时间 : 2017/10/24 14:14
 * 文件描述 :
 * 版权声明 : Copyright (C) 2015-2018 杭州中焯信息技术股份有限公司
 * 修改历史 : 2017/10/24 1.00 初始版本
 * ****************************************************************
 */


public class GridViewAdapter extends BaseAdapter {
    List<Pair<String, Integer>> gridItemList;

    public GridViewAdapter(List<Pair<String, Integer>> gridItemList) {
        this.gridItemList = gridItemList;
    }

    @Override
    public int getCount() {
        return gridItemList == null ? 0 : gridItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridItemList == null ? null : gridItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_shortcut, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.shortcutLabel.setText(gridItemList.get(position).first);
        mViewHolder.shortcutIcon.setImageResource(gridItemList.get(position).second);
        return convertView;
    }

    private class ViewHolder {
        TextView shortcutLabel;
        ImageView shortcutIcon;

        ViewHolder(View view) {
            shortcutLabel = (TextView) view.findViewById(R.id.tv_shortcut_label);
            shortcutIcon = (ImageView) view.findViewById(R.id.iv_shortcut_icon);
        }
    }
}
