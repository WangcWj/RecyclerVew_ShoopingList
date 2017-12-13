package cn.example.wang.relevancerecycleviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by WANG on 17/12/13.
 */

public class MeItemdecoration extends RecyclerView.ItemDecoration {
   Paint paint;

    public MeItemdecoration() {
        if(paint == null){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        if(childAdapterPosition != state.getItemCount()-1){
            outRect.bottom = 3;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int width = parent.getWidth();
        for (int i = 0; i < childCount ; i++) {
            View view = parent.getChildAt(i);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            int bottom = view.getBottom();
            if(childAdapterPosition != state.getItemCount()-1){
                c.drawRect(0,bottom,width,bottom+3,paint);
            }
        }
    }


}
