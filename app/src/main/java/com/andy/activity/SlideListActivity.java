package com.andy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.andy.adapter.SlideListAdapter;
import com.andy.custom.SlideListView;
import com.andy.features.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8 0008.
 */

public class SlideListActivity extends Activity {
    private List<String> datas = new ArrayList<>();
    private SlideListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_list);

        initData();
        setAdapter();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            datas.add("工作 " + i);
        }
    }

    private void setAdapter() {
        list = (SlideListView) findViewById(R.id.list);
        final SlideListAdapter listAdapter = new SlideListAdapter(this, datas);
        list.setAdapter(listAdapter);
        listAdapter.setOnItemDeleteListener(new SlideListAdapter.onItemDeleteListener() {
            @Override
            public void onItemDelete(int position) {
                datas.remove(position);
                listAdapter.notifyDataSetChanged();
            }
        });
    }
}
