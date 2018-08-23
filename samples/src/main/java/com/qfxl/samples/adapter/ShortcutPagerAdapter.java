package com.qfxl.samples.adapter;

import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.qfxl.samples.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @author qfxl
 */
public class ShortcutPagerAdapter extends BasePagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        List<Pair<String, Integer>> itemList = new ArrayList<>();
        GridView gridView = new GridView(container.getContext());
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setVerticalSpacing(30);
        gridView.setHorizontalSpacing(30);
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
