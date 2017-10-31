package com.qfxl.samples.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.qfxl.samples.R;

/**
 * @author qfxl
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
