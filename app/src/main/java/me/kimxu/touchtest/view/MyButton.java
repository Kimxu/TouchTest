package me.kimxu.touchtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import me.kimxu.touchtest.Utils;

/**
 * Created by kimxu on 2016/12/12.
 */

public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private static final String TAG = "实习厨师";
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        boolean action =false;
////        boolean action = super.onTouchEvent(event);
//        Log.w(TAG, "自己去做 " + action + Utils.getEvent(event.getAction()));
//        return action;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w(TAG, "进行思考 " + Utils.getEvent(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }






    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean action =super.dispatchTouchEvent(ev);
//        Log.w(TAG,"分派 "+action+Utils.getEvent(ev.getAction()));
//        return action;
//    }
}
