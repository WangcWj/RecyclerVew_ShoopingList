package cn.example.wang.relevancerecycleviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.example.wang.relevancerecycleviewdemo.adapter.OutSaidAdapter;
import cn.example.wang.relevancerecycleviewdemo.adapter.RecAdapter;
import cn.example.wang.relevancerecycleviewdemo.adapter.RecInnerAdapter;
import cn.example.wang.relevancerecycleviewdemo.bean.DataBean;
import cn.example.wang.relevancerecycleviewdemo.bean.OutBean;
import cn.example.wang.relevancerecycleviewdemo.decoration.CusHeadItemDecoration;
import cn.example.wang.relevancerecycleviewdemo.decoration.MeItemdecoration;
import cn.example.wang.relevancerecycleviewdemo.view.CusRecyclerView;

public class MainActivity extends AppCompatActivity implements CusHeadItemDecoration.InnerScrollPosition,OutSaidAdapter.MeItemClickListener{
    private RecyclerView out_rec;
    private CusRecyclerView recyclerView;
    private RecAdapter recAdapter;
    private List<String> groupName ;
    private CusHeadItemDecoration cusHeadItemDecoration;
    private OutSaidAdapter outSaidAdapter;
    private List<OutBean> outBeanList ;
    private LinearLayoutManager outLinearManager;
    private LinearLayoutManager productLinearManager;
    private boolean move = false;
    private int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupName = new ArrayList<>();
        outBeanList = new ArrayList<>();
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rec);
        out_rec = findViewById(R.id.out_rec);
        initOutRec();
        initRec();
        initData();
        initListener();
    }

    private void initListener() {
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = currentPosition - productLinearManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    recyclerView.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = currentPosition - productLinearManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    recyclerView.scrollBy(0, top);
                }
            }
        }});
    }

    private void initOutRec() {
        outLinearManager = new LinearLayoutManager(this);
        out_rec.setLayoutManager(outLinearManager);
        MeItemdecoration meItemdecoration = new MeItemdecoration();
        out_rec.addItemDecoration(meItemdecoration);
        outSaidAdapter = new OutSaidAdapter(this);
        outSaidAdapter.setMeItemClickListener(this);
        out_rec.setAdapter(outSaidAdapter);
    }

    private void initData() {
        String assetData = getAssetData("sort.json");
        if(TextUtils.isEmpty(assetData)){
            return;
        }

        Gson gson = new Gson();
        DataBean dataBean = gson.fromJson(assetData, DataBean.class);
        List<DataBean.CategoryOneArrayBean> categoryOneArray = dataBean.getCategoryOneArray();
        initGroup(categoryOneArray);
        recAdapter.setRefreshData(categoryOneArray);
    }

    private void initGroup(List<DataBean.CategoryOneArrayBean> categoryOneArray ) {

        for (int i = 0; i < categoryOneArray.size(); i++) {
            String name = categoryOneArray.get(i).getName();
            if(i == 0) {
                outBeanList.add(new OutBean(name, "1"));
            }else {
                outBeanList.add(new OutBean(name, "0"));
            }
            groupName.add(name);
        }
        cusHeadItemDecoration.setGroupName(groupName);
        outSaidAdapter.setData(outBeanList);
    }

    private String getAssetData(String path) {
        String json="";
        try {
            InputStream inputStream = getAssets().open(path);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read =0;
            while ( (read = inputStream.read()) != -1){
                  bos.write(read);
                  bos.flush();
            }
            byte[] bytes = bos.toByteArray();
            json = new String(bytes);
            return  json;
        } catch (IOException e) {
            e.printStackTrace();
        }
      return json;
    }

    private void initRec() {
        productLinearManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(productLinearManager);
        cusHeadItemDecoration = new CusHeadItemDecoration(this);
        cusHeadItemDecoration.setScrollPosition(this);
        recyclerView.addItemDecoration(cusHeadItemDecoration);
        recAdapter = new RecAdapter(this);
        recyclerView.setAdapter(recAdapter);
    }
    //商品列表滚动的监听
    @Override
    public void scrollPosition(int position, String name) {
        boolean touchScrool = recyclerView.isTouchScrool();
        if(touchScrool) {
            outSaidAdapter.setPositionStatus(position);
            moveToCenter(position);
        }
    }
    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = out_rec.getChildAt(position - outLinearManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - out_rec.getHeight() / 2);
            out_rec.smoothScrollBy(0, y);
        }

    }

    //将当前选中的item移到顶部
    private void moveToTop(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = recyclerView.getChildAt(position);
        if (childAt != null) {
            int y = childAt.getTop();
            recyclerView.smoothScrollBy(0, y);
        }

    }

    private void move(int position) {
        int firstItem = productLinearManager.findFirstVisibleItemPosition();
        int lastItem = productLinearManager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            int top = recyclerView.getChildAt(position - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(position);
        }
        move = true;
        currentPosition = position;
    }




    //商品分类的列表
    @Override
    public void meOnClick(View view, int position) {
        recyclerView.setTouchScrool(false);
        outSaidAdapter.setPositionStatus(position);
        moveToCenter(position);
        move(position);
    }
}
