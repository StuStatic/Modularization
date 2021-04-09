package com.example.common.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.R;


/**
 * Created by ChengJinbao
 * 2019年12月02日
 */
public class MyToast {
    public static final int LENGTH_SHORT = 1500;
    public static final int LENGTH_LONG = 3000;
    private Context mContext;
    private TextView mTextView;
    private ImageView mImageView;
    private int mDuration;
    private Dialog mDialog;
    private Handler mHandler;
    private OnDissmiss mOnDissmiss;

    private static class MyToastHolder {
        @SuppressLint("StaticFieldLeak")
        private static final MyToast INSTANCE = new MyToast();
    }

    public static MyToast getInstance() {
        return MyToastHolder.INSTANCE;
    }

    private MyToast() {
    }

    public MyToast init(Context context) {
        try {
            mContext = context;
            mHandler = new Handler();
            mDialog = new Dialog(mContext, R.style.login_MyToastDialogStyle);
            mDialog.setContentView(R.layout.custom_toast);
            mDialog.setOnDismissListener(new DialogDismiss());
            mImageView = mDialog.findViewById(R.id.iv_toast);
            mTextView = mDialog.findViewById(R.id.tv_toast);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void makeTextCenter(Context context, int imgRes, CharSequence text, int duration, OnDissmiss onDissmiss) {
        init(context);
        try {
            mDuration = duration;
            mOnDissmiss = onDissmiss;
            if (imgRes == 0) {
                mImageView.setVisibility(View.GONE);
            } else {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageResource(imgRes);
            }
            mTextView.setText(text);
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeTextCenter(Context context, int imgRes, CharSequence text, int duration) {
        makeTextCenter(context, imgRes, text, duration, null);
    }

    public void makeTextCenter(Context context, int imgRes, int resId, int duration) {
        String msg = "";
        try {
            msg = context.getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        makeTextCenter(context, imgRes, msg, duration, null);
    }

    public void makeText(Context context, CharSequence sequence, int duration) {
        Toast.makeText(context, sequence, duration).show();
    }

    private void show() {
        try {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
                mHandler.postDelayed(() -> {
                    if (mDialog != null) {
                        if (mContext instanceof Activity) {
                            if (!((Activity) mContext).isFinishing())
                                mDialog.dismiss();
                        } else {
                            mDialog.dismiss();
                        }
                    }
                }, mDuration);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showShortToast(Context context, String text) {
        makeTextCenter(context, 0, text, MyToast.LENGTH_SHORT);
    }

    public void showLongToast(Context context, String text) {
        makeTextCenter(context, 0, text, MyToast.LENGTH_LONG);
    }

    private class DialogDismiss implements DialogInterface.OnDismissListener {

        @Override
        public void onDismiss(DialogInterface dialog) {
            if (mOnDissmiss != null) {
                mOnDissmiss.onDissmiss();
            }
        }
    }


    public interface OnDissmiss {
        void onDissmiss();
    }

}
