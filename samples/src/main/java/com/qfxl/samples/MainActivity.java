package com.qfxl.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qfxl.samples.adapter.BannerAdapter;
import com.qfxl.view.viewpager.XViewPager;

/**
 * @author qfxl
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BannerAdapter mBannerAdapter = new BannerAdapter();
        mBannerAdapter.setOnBannerClickListener(new BannerAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(MainActivity.this, "click position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        XViewPager bannerViewPager = findViewById(R.id.xvp_banner);
        bannerViewPager.setAdapter(mBannerAdapter);


        Button bannerBtn = findViewById(R.id.btn_base);
        bannerBtn.setOnClickListener(this);

        Button indicatorBtn = findViewById(R.id.btn_indicator);
        indicatorBtn.setOnClickListener(this);

        Button transformBtn = findViewById(R.id.btn_transform);
        transformBtn.setOnClickListener(this);

        Button othersBtn = findViewById(R.id.btn_others);
        othersBtn.setOnClickListener(this);
    }

    private void startActivity(Class<?> clazz) {
        Intent mIntent = new Intent(this, clazz);
        startActivity(mIntent);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        Class<?> clazz;
        switch (viewId) {
            case R.id.btn_base:
                clazz = BasicUseActivity.class;
                break;
            case R.id.btn_indicator:
                clazz = IndicatorActivity.class;
                break;
            case R.id.btn_transform:
                clazz = TransformActivity.class;
                break;
            case R.id.btn_others:
                clazz = OthersActivity.class;
                break;
            default:
                clazz = BasicUseActivity.class;
        }
        startActivity(clazz);
    }
}
