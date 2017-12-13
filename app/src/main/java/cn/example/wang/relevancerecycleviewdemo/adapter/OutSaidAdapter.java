package cn.example.wang.relevancerecycleviewdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.example.wang.relevancerecycleviewdemo.R;
import cn.example.wang.relevancerecycleviewdemo.bean.OutBean;

/**
 * Created by WANG on 17/12/13.
 */

public class OutSaidAdapter extends RecyclerView.Adapter<OutSaidAdapter.OutSaidViewHolder> {
    private List<OutBean> data;
    private Context context;
    private LayoutInflater inflater;
    private MeItemClickListener meItemClickListener;

    public OutSaidAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (data == null) {
            data = new ArrayList<>();
        }
    }

    public void setData(List<OutBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void setMeItemClickListener(MeItemClickListener meItemClickListener) {
        this.meItemClickListener = meItemClickListener;
    }

    @Override
    public OutSaidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.outsaid_item_layout, parent, false);
        return new OutSaidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OutSaidViewHolder holder, final int position) {
        OutBean s = data.get(position);
        String name = s.getName();
        String status = s.getStatus();
        if ("1".equals(status)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#00ff00"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#e1e1e1"));
        }
        holder.textView.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meItemClickListener != null){
                    meItemClickListener.meOnClick(v,position);
                }
            }
        });
    }

    public void clear() {
        if (data == null || data.size() == 0) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setStatus("0");
        }
        notifyDataSetChanged();
    }

    public void setPositionStatus(int position){
        clear();
        if (data == null || data.size() == 0) {
            return;
        }
        if(position < data.size()) {
            data.get(position).setStatus("1");
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class OutSaidViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public OutSaidViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.outsaid_text);
        }
    }
    public interface MeItemClickListener{

        void meOnClick(View view,int position);
    }
}
