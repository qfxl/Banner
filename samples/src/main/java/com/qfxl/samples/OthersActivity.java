package com.qfxl.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.samples.transform.CardPageTransform;
import com.qfxl.samples.transform.RotateDownPageTransform;
import com.qfxl.view.viewpager.XViewPager;

/**
 * @author qfxl
 */
public class OthersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        XViewPager xViewPager1 = (XViewPager) findViewById(R.id.xvp_others1);
        //如果api < 18 记得手动设置clipChildren
        xViewPager1.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) xViewPager1.getViewPager().getLayoutParams();
        lp.setMargins(40, 0, 40, 0);
        xViewPager1.getViewPager().setLayoutParams(lp);
        xViewPager1.setPageMargin(10)
                .setAdapter(new BannerAdapter());

        XViewPager xViewPager2 = (XViewPager) findViewById(R.id.xvp_others2);
        //如果api < 18 记得手动设置clipChildren
        xViewPager2.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) xViewPager2.getViewPager().getLayoutParams();
        lp2.setMargins(180, 0, 180, 0);
        xViewPager2.getViewPager().setLayoutParams(lp2);
        xViewPager2.setPageMargin(20)
                .setOffscreenPageLimit(3)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new RotateDownPageTransform());

        XViewPager xViewPager3 = (XViewPager) findViewById(R.id.xvp_others3);
        //如果api < 18 记得手动设置clipChildren
        xViewPager3.getViewPager().setClipChildren(false);
        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) xViewPager3.getViewPager().getLayoutParams();
        lp3.setMargins(300, 0, 300, 0);
        xViewPager3.getViewPager().setLayoutParams(lp3);
        xViewPager3.setOffscreenPageLimit(3)
                .setAdapter(new BannerAdapter()).setPageTransformer(false, new CardPageTransform());
    }
}
