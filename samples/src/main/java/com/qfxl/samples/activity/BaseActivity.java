package com.qfxl.samples.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.qfxl.samples.viewbinder.ViewBinder;

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ViewBinder.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewBinder.bind(this);
    }
}
