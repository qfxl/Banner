package com.qfxl.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.view.pagetransform.CubeOutPageTransform;
import com.qfxl.view.pagetransform.DepthPageTransform;
import com.qfxl.view.pagetransform.ZoomOutPageTransformer;
import com.qfxl.view.viewpager.XViewPager;

/**
 * @author qfxl
 */
public class TransformActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        XViewPager xViewPager1 = (XViewPager) findViewById(R.id.xvp_transform_banner1);
        xViewPager1.setAdapter(new BannerAdapter()).setPageTransformer(false, new DepthPageTransform());

        XViewPager xViewPager2 = (XViewPager) findViewById(R.id.xvp_transform_banner2);
        xViewPager2.setUseDefaultIndicator(false)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new CubeOutPageTransform());

        XViewPager xViewPager3 = (XViewPager) findViewById(R.id.xvp_transform_banner3);
        xViewPager3.setAdapter(new BannerAdapter()).setPageTransformer(false, new ZoomOutPageTransformer());
    }
}
