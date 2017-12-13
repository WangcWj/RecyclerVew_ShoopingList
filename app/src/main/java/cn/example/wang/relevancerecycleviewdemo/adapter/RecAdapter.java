package cn.example.wang.relevancerecycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.example.wang.relevancerecycleviewdemo.R;
import cn.example.wang.relevancerecycleviewdemo.bean.DataBean;

/**
 * Created by WANG on 17/12/8.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {
    private List<DataBean.CategoryOneArrayBean> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public RecAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        if(list == null){
            list = new ArrayList<>();
        }
    }


    public void setRefreshData(List<DataBean.CategoryOneArrayBean> data){
        this.list.clear();
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_item_layout, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        DataBean.CategoryOneArrayBean categoryOneArrayBean = list.get(position);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        RecInnerAdapter adapter = new RecInnerAdapter(context);
        holder.recyclerView.setAdapter(adapter);
        List<DataBean.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = categoryOneArrayBean.getCategoryTwoArray();
        adapter.setRefreshData(categoryTwoArray);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RecViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public RecViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.product_item_rec);
        }
    }

}
