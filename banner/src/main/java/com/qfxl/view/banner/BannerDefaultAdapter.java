package com.qfxl.view.banner;
/*
 * Copyright (c) 2018 Frank Xu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : Banner默认适配器，图片。
 *     version: 1.0
 * </pre>
 */
public class BannerDefaultAdapter extends PagerAdapter {
    private List<?> paths;

    private IBannerImageLoader bannerImageLoader;

    public void setBannerImageLoader(IBannerImageLoader bannerImageLoader) {
        this.bannerImageLoader = bannerImageLoader;
    }

    public BannerDefaultAdapter(List<?> paths) {
        this.paths = paths;
    }

    private OnBannerClickListener onBannerClickListener;

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView sImageView = bannerImageLoader.createImageViewView(container.getContext());
        if (sImageView == null) {
            sImageView = new ImageView(container.getContext());
            sImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        sImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerClickListener != null) {
                    onBannerClickListener.onBannerClick(position, paths.get(position));
                }
            }
        });

        bannerImageLoader.displayImage(sImageView.getContext(), sImageView, paths.get(position));
        container.addView(sImageView);
        return sImageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public interface OnBannerClickListener {
        /**
         * banner点击监听
         *
         * @param position
         * @param resource
         */
        void onBannerClick(int position, Object resource);
    }
}
