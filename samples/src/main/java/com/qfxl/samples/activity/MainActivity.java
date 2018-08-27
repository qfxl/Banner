package com.qfxl.samples.activity;

import android.content.Intent;
import android.os.Bundle;

import com.qfxl.samples.R;
import com.qfxl.samples.viewbinder.OnClick;

/**
 * @author qfxl
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btn_base)
    private void openBaseUseActivity() {
        startActivity(BasicSampleActivity.class);
    }

    @OnClick(R.id.btn_indicator)
    private void openIndicatorActivity() {
        startActivity(IndicatorSampleActivity.class);
    }

    @OnClick(R.id.btn_transform)
    private void openTransformActivity() {
        startActivity(TransformSampleActivity.class);
    }

    @OnClick(R.id.btn_others)
    private void openOtherUseActivity() {
        startActivity(OtherSampleActivity.class);
    }

    @OnClick(R.id.btn_recyclerView)
    private void openRecyclerViewActivity() {
        startActivity(IndexActivity.class);
    }

    private void startActivity(Class<?> clazz) {
        Intent mIntent = new Intent(this, clazz);
        startActivity(mIntent);
    }
}
