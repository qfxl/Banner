package com.qfxl.samples.activity;

import android.os.Bundle;

import com.qfxl.samples.R;
import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.indicators.PagerIndicatorViewIndicator;
import com.qfxl.samples.indicators.TextPagerIndicator;
import com.qfxl.samples.viewbinder.BindView;
import com.qfxl.view.banner.Banner;
import com.rd.animation.type.AnimationType;

/**
 * <pre>
 *     author : qfxl
 *     e-mail : xuyonghong0822@gmail.com
 *     time   : 2017/07/19
 *     desc   : 指示器示例
 *     version: 1.0
 * </pre>
 */
public class IndicatorSampleActivity extends BaseActivity {

    @BindView(R.id.banner_indicator_1)
    private Banner indicatorBanner1;

    @BindView(R.id.banner_indicator_2)
    private Banner indicatorBanner2;

    @BindView(R.id.banner_indicator_3)
    private Banner indicatorBanner3;

    @BindView(R.id.banner_indicator_4)
    private Banner indicatorBanner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        indicatorBanner1
                .setAutoStart(true)
                .setInfinityLoop(true)
                .setIsDefaultIndicator(false)
                .setLoopInterval(1000)
                .setAdapter(new BannerAdapter())
                .setPagerIndicator(new PagerIndicatorViewIndicator(this));

        PagerIndicatorViewIndicator pagerIndicatorViewIndicator = new PagerIndicatorViewIndicator(this);
        pagerIndicatorViewIndicator.setAnimationType(AnimationType.SWAP);

        indicatorBanner2
                .setAutoStart(true)
                .setInfinityLoop(true)
                .setIsDefaultIndicator(false)
                .setLoopInterval(1000)
                .setAdapter(new BannerAdapter())
                .setPagerIndicator(pagerIndicatorViewIndicator);

        indicatorBanner3.setAutoStart(true)
                .setInfinityLoop(true)
                .setLoopInterval(1000)
                .setAdapter(new BannerAdapter())
                //自定义指示器务必在setAdapter之后调用
                .setPagerIndicator(new TextPagerIndicator(this));

        indicatorBanner4
                .setAdapter(new BannerAdapter());
    }
}
