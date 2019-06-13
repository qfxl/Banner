package com.qfxl.samples.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.qfxl.samples.R;
import com.qfxl.samples.adapter.IndexAdapter;
import com.qfxl.samples.viewbinder.BindView;

public class IndexActivity extends BaseActivity {
    @BindView(R.id.rv_index)
    private RecyclerView indexRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        indexRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        indexRv.setAdapter(new IndexAdapter());
    }
}
