package com.example.common.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

/**
 * Created by ChengJinbao
 * 2019年12月03日
 */
public class LoadingUtils {
    private volatile Dialog mDialog;

    private LoadingUtils() {
    }

    private static class LoadingUtilsHolder {
        private static final LoadingUtils INSTANCE = new LoadingUtils();
    }

    public static LoadingUtils getInstance() {
        return LoadingUtilsHolder.INSTANCE;
    }

    public void showLoading(Context context) {
        showLoading(context, "加载中...");
    }

    public void showUploading(Context context) {
        showLoading(context, "上传中...");
    }

    public void showLoading(Context context, CharSequence sequence) {
        mDialog = new Dialog(context, R.style.login_MyToastDialogStyle);
        mDialog.setContentView(R.layout.custom_loading);
        ImageView imageView = mDialog.findViewById(R.id.loading);
        TextView textView = mDialog.findViewById(R.id.tv_loading);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.loading);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.setAnimation(operatingAnim);
        textView.setText(sequence);
        mDialog.setCanceledOnTouchOutside(false);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dissmiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
