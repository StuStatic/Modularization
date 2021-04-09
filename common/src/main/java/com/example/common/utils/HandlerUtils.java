package com.example.common.utils;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * <p>
 * created by mufaith at 2021/01/30
 **/
public class HandlerUtils {
    private static final String TAG = "HandlerUtils";
    private static Handler sUIHandler = new Handler(Looper.getMainLooper());
    private static Handler sWorkingHandler;
    private static final HandlerThread mWorkingThread = new HandlerThread(TAG);

    static {
        mWorkingThread.start();
        sWorkingHandler = new Handler(mWorkingThread.getLooper());
    }

    public static Handler getUIHandler() {
        return sUIHandler;
    }

    public static Handler getWorkingHandler() {
        return sWorkingHandler;
    }
}
