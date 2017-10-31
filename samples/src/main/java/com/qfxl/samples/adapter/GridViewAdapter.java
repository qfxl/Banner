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
 * @author qfxl
 */
public class GridViewAdapter extends BaseAdapter {
   private List<Pair<String, Integer>> gridItemList;

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
