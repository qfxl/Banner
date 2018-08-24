package com.qfxl.view.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public interface IBannerImageLoader {
    /**
     * 加载图片
     *
     * @param context
     * @param imageView
     * @param path
     */
    void displayImage(Context context, ImageView imageView, Object path);

    /**
     * create Banner View
     * @param context
     * @return
     */
    ImageView createImageViewView(Context context);
}
