package com.andy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andy.adapter.FeaturesAdapter;
import com.andy.features.R;
import com.andy.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FeaturesAdapter.OnItemClickListener {
    private RecyclerView features_list;
    private List<String> data = new ArrayList<>();
    private FeaturesAdapter mAdapter;
    private List<Class> mActivity = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        getData();
        initView();
    }

    private void findView() {
        features_list = (RecyclerView) findViewById(R.id.features_list);
    }

    private void getData() {
        data.add("图片缩放移动");
        mActivity.add(ImageScale.class);

        data.add("图片选择");
        mActivity.add(ImageChoose.class);
    }

    private void initView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new FeaturesAdapter(data);
        features_list.setLayoutManager(mLayoutManager);
        features_list.setAdapter(mAdapter);
        features_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void OnClick(View view, int position) {
        Intent intent = new Intent(this, mActivity.get(position));
        startActivity(intent);
    }
}
