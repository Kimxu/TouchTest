package me.kimxu.touchtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import me.kimxu.touchtest.view.MyButton;

public class MainActivity extends AppCompatActivity {

    private MyButton myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton= (MyButton) findViewById(R.id.myButton);
//        myButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.w("实习厨师触碰","触碰我了");
//
//                return true;
//            }
//        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("实习厨师点击","点击我了");
            }
        });
    }


    private static final String TAG = "服务员";
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean action = super.onTouchEvent(event);
        Log.i(TAG, "这道菜做不了 " + action + Utils.getEvent(event.getAction()));
        return action;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "传递一个菜单 " + Utils.getEvent(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }



    //    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        boolean action =super.dispatchTouchEvent(ev);
//        Log.i(TAG,"分派 "+action+Utils.getEvent(ev.getAction()));
//        return action;
//    }
}
