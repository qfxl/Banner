package com.qfxl.samples.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.qfxl.samples.R;
import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.indicators.PagerIndicatorViewIndicator;
import com.qfxl.samples.indicators.SampleIndicator;
import com.qfxl.samples.indicators.TextPagerIndicator;
import com.qfxl.samples.viewbinder.BindView;
import com.qfxl.view.banner.Banner;
import com.qfxl.view.banner.BannerDefaultAdapter;
import com.qfxl.view.banner.IBannerImageLoader;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.List;

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

    private static List<Integer> imageIds = new ArrayList<>();

    static {
        imageIds.add(R.drawable.banner_1);
        imageIds.add(R.drawable.banner_2);
        imageIds.add(R.drawable.banner_3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);

        indicatorBanner1
                .setIsDefaultIndicator(false)
                .setLoopInterval(1000)
                .setPagerIndicator(new PagerIndicatorViewIndicator(this))
                .setImageLoader(new IBannerImageLoader() {
                    @Override
                    public void displayImage(Context context, ImageView imageView, Object path) {
                        imageView.setImageResource((Integer) path);
                    }

                    @Override
                    public ImageView createImageViewView(Context context) {
                        return null;
                    }
                })
                .setImageResources(imageIds)
                .setBannerCLickListener(new BannerDefaultAdapter.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object resource) {
                        Toast.makeText(IndicatorSampleActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .ready();

        PagerIndicatorViewIndicator pagerIndicatorViewIndicator = new PagerIndicatorViewIndicator(this);
        pagerIndicatorViewIndicator.setAnimationType(AnimationType.SWAP);

        indicatorBanner2
                .setIsDefaultIndicator(false)
                .setLoopInterval(1000)
                .setPagerIndicator(pagerIndicatorViewIndicator)
                .setAdapter(new BannerAdapter());


        indicatorBanner3
                .setIsDefaultIndicator(false)
                .setLoopInterval(1000)
                .setPagerIndicator(new TextPagerIndicator(this))
                .setAdapter(new BannerAdapter());


        indicatorBanner4
                .setPagerIndicator(new SampleIndicator(this))
                .setAdapter(new BannerAdapter());
    }
}
