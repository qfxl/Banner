package com.qfxl.samples.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qfxl.samples.R;
import com.qfxl.samples.Utils;
import com.qfxl.samples.adapter.ShortcutPagerAdapter;
import com.qfxl.samples.viewbinder.BindView;
import com.qfxl.view.banner.Banner;
import com.qfxl.view.banner.BannerDefaultAdapter;
import com.qfxl.view.banner.IBannerImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qfxl
 * 基础用法
 */
public class BasicSampleActivity extends BaseActivity {
    @BindView(R.id.banner_basic_banner)
    private Banner bannerViewPager;

    @BindView(R.id.banner_basic_banner2)
    private Banner bannerViewPager2;

    @BindView(R.id.banner_shortcut)
    private Banner shortcutBanner;

    private static List<String> urls = new ArrayList<>();

    static {
        urls.add("https://i0.hdslb.com/bfs/archive/9fff0328cbfec3e16a8de0af570399e4870f0fa8.jpg");
        urls.add("https://i0.hdslb.com/bfs/archive/b8efabf3031888db680f39dda98a41a7f987e4a9.jpg");
        urls.add("https://i0.hdslb.com/bfs/archive/a6be3a5d4a4adc4193a09b748d2d8266c56b118b.jpg");
        urls.add("https://i0.hdslb.com/bfs/sycp/tmaterial/201807/26c8f387a0777961a9ed804bd88accb5.png");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        bannerViewPager.autoReady(urls, imageLoader, new BannerDefaultAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position, Object resource) {

            }
        });

        bannerViewPager2.setAutoLoop(true)
                .setInfinityLoop(true)
                .setLoopInterval(1000)
                .setTouchScrollable(true)
                .setIsDefaultIndicator(true)
                .setIndicatorLayoutBackgroundColor(Color.TRANSPARENT)
                .setIndicatorPosition(Banner.IndicatorPosition.BOTTOM)
                .setIndicatorGravity(Gravity.CENTER)
                .setIndicatorHeight(Utils.dp2px(this, 16))
                .setIndicatorItemWidth(Utils.dp2px(this, 6))
                .setIndicatorItemHeight(Utils.dp2px(this, 6))
                .setIndicatorItemMargin(Utils.dp2px(this, 3))
                .setIndicatorNormalResId(R.drawable.shape_default_indicator_normal)
                .setIndicatorSelectResId(R.drawable.shape_default_indicator_select)
                .setOffscreenPageLimit(3)
                .setScrollDuration(600)
                .setBannerCLickListener(new BannerDefaultAdapter.OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position, Object resource) {

                    }
                })
                .setImageResources(urls)
                .setImageLoader(imageLoader)
                .ready();

        shortcutBanner.setAdapter(new ShortcutPagerAdapter());
    }

    private IBannerImageLoader imageLoader = new IBannerImageLoader() {
        @Override
        public void displayImage(Context context, ImageView imageView, Object path) {
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.img_placeholder)).load(path).into(imageView);
        }

        @Override
        public ImageView createImageViewView(Context context) {
            return null ;
        }
    };
}
