package me.kimxu.touchtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import me.kimxu.touchtest.Utils;

/**
 * Created by kimxu on 2016/12/12.
 */

public class MyViewGroup2 extends LinearLayout {

    private static String TAG = "厨师长2";

    public MyViewGroup2(Context context) {
        super(context);
    }

    public MyViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean action =false;
        boolean action = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "是否自己去做？ " + action + Utils.getEvent(ev.getAction()));
        return action;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean action = super.onTouchEvent(event);
        Log.d(TAG, "自己做 " + action + Utils.getEvent(event.getAction()));
        return action;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean action =super.dispatchTouchEvent(ev);
//        Log.d(TAG,"分派 "+action+Utils.getEvent(ev.getAction()));
//        return action;
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "进行思考 " + Utils.getEvent(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
}
