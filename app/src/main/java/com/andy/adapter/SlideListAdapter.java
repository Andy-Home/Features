package com.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.features.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8 0008.
 */

public class SlideListAdapter extends BaseAdapter {

    private Context context;
    private List<String> datas;

    public SlideListAdapter(Context context, List<String> strings) {
        this.context = context;
        this.datas = strings;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_slide_list, viewGroup, false);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.delete = (TextView) view.findViewById(R.id.delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(datas.get(i));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "删除第 " + i + " 项", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder {
        public TextView name;
        public TextView delete;
    }
}
