package me.kimxu.touchtest;

import android.view.MotionEvent;

/**
 * Created by kimxu on 2016/12/12.
 */

public class Utils {

    public static String getEvent(int event) {
        switch (event) {
            case MotionEvent.ACTION_DOWN:
                return " ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return " ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return " ACTION_UP";
            default:
                return " DEFAULT";
        }

    }
}
