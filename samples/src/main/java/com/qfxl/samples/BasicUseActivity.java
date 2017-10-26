package com.qfxl.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.adapter.ShortcutPagerAdapter;
import com.qfxl.view.viewpager.XViewPager;

public class BasicUseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        XViewPager bannerViewPager = (XViewPager) findViewById(R.id.xvp_banner);
        bannerViewPager.setAutoLoop(true)
                .setLoopInterval(2000)
                .setAdapter(new BannerAdapter());

        XViewPager shortcutViewPager = (XViewPager) findViewById(R.id.xvp_shortcut);
        shortcutViewPager.setAdapter(new ShortcutPagerAdapter());
    }

}
