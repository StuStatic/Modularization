package com.example.common.utils;


import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * <p>
 * created by MuFaith at 2021/01/19
 **/
public class AppInfoUtils {

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}

