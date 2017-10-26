package com.qfxl.samples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author qfxl
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bannerBtn = (Button) findViewById(R.id.btn_base);
        bannerBtn.setOnClickListener(this);

        Button indicatorBtn = (Button) findViewById(R.id.btn_indicator);
        indicatorBtn.setOnClickListener(this);
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
            default:
                clazz = BasicUseActivity.class;
        }
        startActivity(clazz);
    }
}
