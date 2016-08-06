package com.kklv.bmoe.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志统一管理类
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/17 17:30
 */
public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "kklv";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.i(TAG, msg);
        }

    }

    public static void d(String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.d(TAG, msg);
        }

    }

    public static void e(String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.e(TAG, msg);
        }

    }

    public static void v(String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.v(TAG, msg);
        }

    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.i(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.d(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.e(tag, msg);
        }

    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            msg = ((msg == null) ? "" : msg);
            Log.v(tag, msg);
        }

    }
}
