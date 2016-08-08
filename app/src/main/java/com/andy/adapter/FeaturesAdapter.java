package com.andy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.features.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.ViewHolder> implements View.OnClickListener{
    private List<String> datas = null;
    private OnItemClickListener mListener;

    /**
     * 监听接口
     */
    public interface OnItemClickListener{
        void OnClick(View view, int position);
    }

    public FeaturesAdapter(List<String> datas){
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_features, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(datas.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View view) {
        mListener.OnClick(view, (int) view.getTag());
    }

    /**
     * 设置接口
     *
     * @param mListener
     */
    public void setOnItemClickListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name);
        }
    }


}
