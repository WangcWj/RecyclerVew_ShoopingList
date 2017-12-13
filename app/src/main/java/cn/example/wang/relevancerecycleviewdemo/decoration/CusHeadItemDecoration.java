package cn.example.wang.relevancerecycleviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANG on 17/12/8.
 */

public class CusHeadItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private int headHeight ;
    private Paint headPaint;
    private Paint textPaint;
    private Rect textRect;
    private List<String> groupName ;
    private int prePosition =-1;
    private InnerScrollPosition scrollPosition;

    public CusHeadItemDecoration(Context context) {
        this.context = context;
        init();
    }

    public void setScrollPosition(InnerScrollPosition scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    private void init() {
        if(context == null){
            return;
        }
        headHeight = dip2px(context,40);
        headPaint = new Paint();
        headPaint.setAntiAlias(true);
        headPaint.setStyle(Paint.Style.FILL);
        headPaint.setColor(Color.parseColor("#ffffff"));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.setTextSize(dip2px(context,15));

        List<String> groupName = getGroupName();
        textRect = new Rect();
        if(groupName == null){
            groupName = new ArrayList<>();
        }
    }
    //事先设置好
    public void setGroupName(List<String> groupName) {
        this.groupName = groupName;
    }

    public List<String> getGroupName() {
        return groupName;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = headHeight;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int paddingTop = parent.getPaddingTop();
        int paddingRight = parent.getPaddingRight();
        int paddingLeft = parent.getPaddingLeft();
        int right = parent.getWidth() - paddingRight;
        int childCount = parent.getChildCount();

        for (int i = 0; i <childCount; i++) {
            View childView = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(childView);
            int top = childView.getTop();
            int bottom = childView.getBottom();
            int textY = Math.max(top,headHeight);

            if(i+ 1 < childCount){
                View childAt = parent.getChildAt( i+ 1);
                int top1 = childAt.getTop();
                if(top1 <= headHeight*2){
                   textY = bottom;
                }
            }

            c.drawRect(paddingLeft,textY-headHeight,right,textY,headPaint);
            String name = groupName.get(childAdapterPosition);
            if(TextUtils.isEmpty(name)){
                name="";
            }
            textPaint.getTextBounds(name,0,name.length(),textRect);
            c.drawText(name,dip2px(context,10),textY-(headHeight - textRect.height()) /2,textPaint);
            if(textY == headHeight){
                if(prePosition != childAdapterPosition) {
                    if(scrollPosition != null){
                        scrollPosition.scrollPosition(childAdapterPosition,groupName.get(childAdapterPosition));
                    }
                    Log.e("WANG", "Item Position " + childAdapterPosition);
                    prePosition = childAdapterPosition;
                }
            }
        }
    }

    public int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);
    }

    public interface InnerScrollPosition{
        void scrollPosition(int position ,String name);
    }
}
