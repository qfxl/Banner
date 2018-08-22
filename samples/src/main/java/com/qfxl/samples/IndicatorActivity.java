package com.qfxl.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.indicators.LineIndicator;
import com.qfxl.samples.indicators.TextPagerIndicator;
import com.qfxl.view.viewpager.XViewPager;

/**
 * @author qfxl
 */
public class IndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        XViewPager xViewPager1 = findViewById(R.id.xvp_indicator_1);
        xViewPager1.setAutoLoop(true)
                .setIndicatorItemWidth(30)
                .setIndicatorItemHeight(30)
                .setIndicatorNormalResId(R.drawable.indicator_pentagram_normal)
                .setDefaultIndicatorSelectResId(R.drawable.indicator_pentagram_selected)
                .setAdapter(new BannerAdapter());

        XViewPager xViewPager2 = findViewById(R.id.xvp_indicator_2);
        xViewPager2.setAutoLoop(true)
                .setIndicatorItemWidth(30)
                .setIndicatorItemHeight(30)
                .setIndicatorPosition(XViewPager.IndicatorPosition.TOP)
                .setDefaultIndicatorGravity(Gravity.RIGHT)
                .setIndicatorNormalResId(R.drawable.indicator_flights_normal)
                .setDefaultIndicatorSelectResId(R.drawable.indicator_flights_selected)
                .setAdapter(new BannerAdapter());

        XViewPager xViewPager3 = findViewById(R.id.xvp_indicator_3);
        xViewPager3.setAutoLoop(true)
                .setAdapter(new BannerAdapter())
                //自定义指示器务必在setAdapter之后调用
                .setPagerIndicator(new TextPagerIndicator(this));

        XViewPager xViewPager4 = findViewById(R.id.xvp_indicator_4);
        xViewPager4.setAutoLoop(false)
                .setEnableInfinityLoop(false)
                .setAdapter(new BannerAdapter())
                //自定义指示器务必在setAdapter之后调用
                .setPagerIndicator(new LineIndicator(this));
    }
}
