package com.qfxl.samples;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.adapter.ShortcutPagerAdapter;
import com.qfxl.view.viewpager.XViewPager;

/**
 * @author qfxl
 */
public class BasicUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        BannerAdapter mBannerAdapter = new BannerAdapter();
        mBannerAdapter.setOnBannerClickListener(new BannerAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BasicUseActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        XViewPager bannerViewPager = (XViewPager) findViewById(R.id.xvp_basic_banner);
        bannerViewPager.setAdapter(mBannerAdapter);

        XViewPager bannerViewPager2 = (XViewPager) findViewById(R.id.xvp_basic_banner2);
        bannerViewPager2.setAutoLoop(true)
                .setEnableInfinityLoop(true)
                .setLoopInterval(1000)
                .setTouchScrollable(true)
                .setIndicatorLayoutBackgroundColor(Color.TRANSPARENT)
                .setIndicatorPosition(XViewPager.IndicatorPosition.BOTTOM)
                .setOffscreenPageLimit(1)
                .setUseDefaultIndicator(true)
                .setDefaultIndicatorGravity(Gravity.CENTER)
                .setDefaultIndicatorNormalResId(R.drawable.shape_default_indicator_normal)
                .setDefaultIndicatorSelectResId(R.drawable.shape_default_indicator_select)
                .setDefaultIndicatorSize(20)
                .setDefaultIndicatorMargin(10)
                .setAdapter(mBannerAdapter);

        XViewPager shortcutViewPager = (XViewPager) findViewById(R.id.xvp_shortcut);
        shortcutViewPager.setAdapter(new ShortcutPagerAdapter());
    }

}
