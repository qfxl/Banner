package com.qfxl.samples.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.qfxl.samples.R;
import com.qfxl.samples.Utils;
import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.viewbinder.BindView;
import com.qfxl.view.pagetransform.CardPageTransform;
import com.qfxl.view.pagetransform.RotateDownPageTransform;
import com.qfxl.view.banner.Banner;

/**
 * @author qfxl
 * 演示一些常见效果
 */
public class OtherSampleActivity extends BaseActivity {
    @BindView(R.id.banner_others1)
    Banner otherBanner1;

    @BindView(R.id.banner_others2)
    Banner otherBanner2;

    @BindView(R.id.banner_others3)
    Banner otherBanner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        //如果api < 18 记得手动设置clipChildren
        otherBanner1.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) otherBanner1.getViewPager().getLayoutParams();
        lp.setMargins(Utils.dp2px(this, 20), 0, Utils.dp2px(this, 20), 0);
        otherBanner1.getViewPager().setLayoutParams(lp);
        otherBanner1.setPageMargin(Utils.dp2px(this, 10))
                .setOffscreenPageLimit(3)
                .setAdapter(new BannerAdapter());

        //如果api < 18 记得手动设置clipChildren
        otherBanner2.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) otherBanner2.getViewPager().getLayoutParams();
        lp2.setMargins(Utils.dp2px(this, 60), 0, Utils.dp2px(this, 60), 0);
        otherBanner2.getViewPager().setLayoutParams(lp2);
        otherBanner2.setPageMargin(20)
                .setOffscreenPageLimit(3)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new RotateDownPageTransform());

        //如果api < 18 记得手动设置clipChildren
        otherBanner3.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) otherBanner3.getViewPager().getLayoutParams();
        lp3.setMargins(Utils.dp2px(this, 50), 0, Utils.dp2px(this, 50), 0);
        otherBanner3.getViewPager().setLayoutParams(lp3);
        otherBanner3.setOffscreenPageLimit(3)
                .setAutoStart(true)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new CardPageTransform());
    }
}
