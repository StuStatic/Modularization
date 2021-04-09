package com.example.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

public class ToastHelper {

    private static Toast sToast;

    private static Toast ViewToast;


    private ToastHelper() {

    }

    public static void showToast(Context context, String text) {
//        showToastInner(context, text, Toast.LENGTH_SHORT);
        showCustomToast(context, text);
    }

    public static void showToast(Context context, int stringId) {
//        showToastInner(context, context.getString(stringId), Toast.LENGTH_SHORT);
        showCustomToast(context, context.getString(stringId));
    }


    public static void showToastLong(Context context, String text) {
        showToastInner(context, text, Toast.LENGTH_LONG);
    }

    public static void showToastLong(Context context, int stringId) {
        showToastInner(context, context.getString(stringId), Toast.LENGTH_LONG);
    }


    private static void showToastInner(Context context, String text, int duration) {
        ViewToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        ViewToast.setGravity(Gravity.CENTER, 0, 0);
        ViewToast.setText(text);
        ViewToast.show();
    }

    public static void showCustomToast(Context context, String text) {
        if (context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.P || Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setText(text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (sToast == null) {
                ensureToast(context);
            }
            /* toast 正在显示未消失时，先隐藏再显示*/
//            if (sToast.getView().getWindowVisibility() == View.VISIBLE) {
//                sToast.cancel();
//            }
            sToast.setText(text);
            sToast.setGravity(Gravity.CENTER, 0, 0);
            sToast.show();
        }
    }

    public static void showToastWithoutAppName(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setText(text);
        toast.show();
    }

    @SuppressLint("ShowToast")
    private static void ensureToast(Context context) {
        if (sToast != null) {
            return;
        }
        synchronized (ToastHelper.class) {
            if (sToast != null) {
                return;
            }
            sToast = Toast.makeText(context, " ", Toast.LENGTH_SHORT);
        }
    }
}
