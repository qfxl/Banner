package com.qfxl.samples.activity;

import android.os.Bundle;

import com.qfxl.samples.R;
import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.viewbinder.BindView;
import com.qfxl.view.pagetransform.CubeOutPageTransform;
import com.qfxl.view.pagetransform.DepthPageTransform;
import com.qfxl.view.pagetransform.ZoomOutPageTransformer;
import com.qfxl.view.banner.Banner;

/**
 * @author qfxl
 * 动画Sample
 */
public class TransformSampleActivity extends BaseActivity {
    @BindView(R.id.banner_transform_1)
    Banner banner1;

    @BindView(R.id.banner_transform_2)
    Banner banner2;

    @BindView(R.id.banner_transform_3)
    Banner banner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        banner1.setIsDefaultIndicator(false)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new DepthPageTransform());

        banner2.setIsDefaultIndicator(false)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new CubeOutPageTransform());

        banner3.setIsDefaultIndicator(false)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new ZoomOutPageTransformer());
    }
}
