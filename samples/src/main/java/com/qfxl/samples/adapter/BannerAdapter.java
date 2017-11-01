package com.qfxl.samples.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qfxl.samples.R;

/**
 * @author qfxl
 */
public class BannerAdapter extends BasePagerAdapter {
    public interface OnBannerClickListener {
        /**
         * banner点击监听
         * @param position
         */
        void onBannerClick(int position);
    }

    private OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView mImageView = new ImageView(container.getContext());
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerClickListener != null) {
                    onBannerClickListener.onBannerClick(position);
                }
            }
        });
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
