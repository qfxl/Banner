package com.qfxl.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        XViewPager bannerViewPager = (XViewPager) findViewById(R.id.xvp_banner);
        bannerViewPager.setAutoLoop(true)
                .setLoopInterval(2000)
                .setAdapter(mBannerAdapter);

        XViewPager shortcutViewPager = (XViewPager) findViewById(R.id.xvp_shortcut);
        shortcutViewPager.setAdapter(new ShortcutPagerAdapter());
    }

}
