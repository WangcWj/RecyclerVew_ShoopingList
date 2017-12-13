package cn.example.wang.relevancerecycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.example.wang.relevancerecycleviewdemo.R;
import cn.example.wang.relevancerecycleviewdemo.bean.DataBean;

/**
 * Created by WANG on 17/12/8.
 */

public class RecInnerAdapter extends RecyclerView.Adapter<RecInnerAdapter.RecViewHolder> {
    private List<DataBean.CategoryOneArrayBean.CategoryTwoArrayBean> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecInnerAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        if(list == null){
            list = new ArrayList<>();
        }
    }

    public void setRefreshData(List<DataBean.CategoryOneArrayBean.CategoryTwoArrayBean> data){
         this.list.clear();
         this.list.addAll(data);
         notifyDataSetChanged();
    }

    @Override
    public RecInnerAdapter.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_inner_item_layout, parent, false);
        return new RecInnerAdapter.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecInnerAdapter.RecViewHolder holder, int position) {
        DataBean.CategoryOneArrayBean.CategoryTwoArrayBean categoryOneArrayBean = list.get(position);
        String name = categoryOneArrayBean.getName();
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public RecViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.product_inner_title_text);
        }
    }

}
