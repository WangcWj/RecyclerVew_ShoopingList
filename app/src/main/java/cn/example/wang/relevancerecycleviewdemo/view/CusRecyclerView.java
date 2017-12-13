package cn.example.wang.relevancerecycleviewdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by WANG on 17/12/13.
 */

public class CusRecyclerView extends RecyclerView {

    private float touchSlop;
    private float startDx;
    private float startDy;
    public boolean isTouchScrool = false;

    public CusRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public CusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public boolean isTouchScrool() {
        return isTouchScrool;
    }

    public void setTouchScrool(boolean touchScrool) {
        isTouchScrool = touchScrool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        isTouchScrool = false;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDx = e.getX();
                startDy = e.getY();
                break;
            case MotionEvent.ACTION_UP:
                float x = e.getX();
                float y = e.getY();
                float moveY = Math.abs(y - startDy);
                if(moveY > touchSlop){
                    isTouchScrool = true;
                    Log.e("WANG","CusRecyclerView.onTouchEvent."+isTouchScrool );
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isTouchScrool = true;
                break;
        }
        return super.onTouchEvent(e);
    }
}
