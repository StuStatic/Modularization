package com.example.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import com.example.common.BuildConfig;
import com.example.common.R;
import com.example.common.bean.ShareBean;
import com.example.common.bean.ShareInWebBean;
import com.example.common.constants.ConstantsKt;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.net.URL;
import java.util.Map;

public class ShareUtil {
    private static ShareUtil instance;
    private OnShareListener onShareListener;
    private Context context;

    private ShareBean shareBean;

    private ShareUtil(Context context) {
        this.context = context;
    }

    public static ShareUtil getInstance(Context context) {
        if (instance == null) {
            instance = new ShareUtil(context);
        }
        return instance;
    }


    public enum ShareType {
        //微信好友
        WX_FRIENDS,
        //微信小程序
        WX_MINI,
        //微信朋友圈
        WX_CIRCLE,
        QQ,
        QQ_SPACE,
        WB,
    }

    public enum ShareFrom {
        //作品
        WORK,
        //用户中心 作品集
        USER_CENTER,
        //课程详情
        CLASSES_DETAIL,
        //我的报告
        MY_REPORT,
        //如何上课
        HOW_GO_CLASSES,
    }


    /**
     * 获取分享的图片
     */
    private Bitmap getShareBitmap() {
        Bitmap bitmap = BitmapUtil.getBitmap(context, shareBean.getCover(), 200, 250);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }
        return bitmap;
    }

    /**
     * 获取分享的图片
     */
    private Bitmap getShareBitmap(String cover, int localImageId, int width, int height) {
        Bitmap bitmap = BitmapUtil.getBitmap(context, cover, width, height);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), localImageId);
        }
        return bitmap;
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }


    public interface OnShareListener {
        void onError(String msg);

        void onComplete();
    }

    public void setOnShareListener(OnShareListener listener) {
        onShareListener = listener;
    }

    public void dismissDialogBySuccess() {
        if (onShareListener != null) {
            onShareListener.onComplete();
        }
    }

    public void dismissDialogByError(String msg) {
        if (onShareListener != null) {
            onShareListener.onError(msg);
        }
    }


    // 内部浏览器中网页 打开分享
    public void shareToWXFromWeb(ShareInWebBean bean) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, ConstantsKt.APP_FOR_WX);
        iwxapi.registerApp(ConstantsKt.APP_FOR_WX);
        if (!iwxapi.isWXAppInstalled()) {
            if (onShareListener != null) {
                onShareListener.onError(context.getResources().getString(R.string.we_chat_not_install));
            }
            return;
        }
        WXWebpageObject webPage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webPage);
        webPage.webpageUrl = bean.getUrl();
        msg.title = bean.getTitle();
        new Thread(() -> {
            try {
                //网络图片
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(bean.getCover()).openStream());
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                }
                msg.description = bean.getDesc();
                msg.thumbData = BitmapUtil.getBitmapBytes(bitmap, false);
                //构造一个req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = System.currentTimeMillis() + "";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
            } catch (Exception e) {
                e.printStackTrace();
                logPrint("分享缩略图出错");
            }
        }).start();
    }

    public void openWX() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        if (intent == null) {
            ToastHelper.showToast(context, context.getResources().getString(R.string.we_chat_not_install));
            return;
        }
        context.startActivity(intent);
    }


    private void logPrint(String msg) {
        Log.e("share ", msg);
    }


    //分享链接
    public void shareLink(ShareBean bean, ShareType shareType) {
        this.shareBean = bean;
        switch (shareType) {
            case WX_FRIENDS:
                shareLinkToWX(false);
                break;
            case WX_CIRCLE:
                shareLinkToWX(true);
                break;
        }
    }

    private void shareLinkToWX(boolean isPYQ) {

        IWXAPI iwxapi = getWXApi();
        if (iwxapi == null) {
            return;
        }
        WXWebpageObject webPage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webPage);
        webPage.webpageUrl = shareBean.getUrl();
        if(isPYQ){
            if(TextUtils.isEmpty(shareBean.getPyqTitle())){
                msg.title = shareBean.getTitle();
            }else {
                msg.title = shareBean.getPyqTitle();
            }
            if(TextUtils.isEmpty(shareBean.getPyqDesc())){
                msg.description = shareBean.getDesc();
            }else {
                msg.description = shareBean.getPyqDesc();
            }
        }else {
            msg.title = shareBean.getTitle();
            msg.description = shareBean.getDesc();
        }

        new Thread(() -> {
            Bitmap bitmap = getShareBitmap(shareBean.getCover(), shareBean.getLocalUrl(), 200, 200);
            msg.thumbData = BitmapUtil.getBitmapBytes(bitmap, false);
            //构造一个req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = System.currentTimeMillis() + "";
            req.message = msg;
            req.scene = isPYQ ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            req.userOpenId = ConstantsKt.APP_FOR_WX;
            iwxapi.sendReq(req);
        }).start();
    }


    //分享小程序到微信好友
    public void shareMiniToWXFriend(ShareBean bean) {
        this.shareBean = bean;
        IWXAPI iwxapi = getWXApi();
        if (iwxapi == null) {
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        if (BuildConfig.DEBUG) {
            miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_PREVIEW;// 正式版:0，测试版:1，体验版:2
        }
        miniProgramObj.userName = ConstantsKt.APP_FOR_WX_MINI; // 小程序原始id

        miniProgramObj.path = urlJoint(shareBean.getWxMiniUrl(), shareBean.getWxMiniParams());
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = shareBean.getDesc();
        msg.description = shareBean.getDesc();
        new Thread(() -> {
            Bitmap bitmap = getShareBitmap(shareBean.getCover(), shareBean.getLocalUrl(), 200, 200);
            //默认设置分享图片比例宽比高 4：3 从页面中间位置截图
            //为了防止部分图片裁剪异常，需处理
            Bitmap optionBitmap;
            float whScale = bitmap.getWidth() * 1.0f / bitmap.getHeight();
            if (whScale > (4 / 3.0f)) {
                int neww = bitmap.getHeight() * 4 / 3, startx = bitmap.getWidth() / 2 - neww / 2;
                optionBitmap = Bitmap.createBitmap(bitmap, startx, 0,
                        neww, bitmap.getHeight());
            } else {
                int newh = bitmap.getWidth() * 3 / 4, starty = bitmap.getHeight() / 2 - newh / 2;
                optionBitmap = Bitmap.createBitmap(bitmap, 0, starty,
                        bitmap.getWidth(), newh);
            }

            msg.thumbData = BitmapUtil.compressImage(optionBitmap, 127, 20);
            //构造一个req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = System.currentTimeMillis() + "";
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneSession;
            iwxapi.sendReq(req);
        }).start();
    }

    public void shareImageToWXPYQ(ShareBean shareBean, Bitmap bitmap) {
        IWXAPI iwxapi = getWXApi();
        if (iwxapi == null) {
            return;
        }
        if (bitmap == null) {
            return;
        }
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
        thumbBmp = BitmapUtil.compressBitmap(thumbBmp, 31, 50);
        mediaMessage.thumbData = BitmapUtil.getBitmapBytes(thumbBmp, true);
        mediaMessage.title = shareBean.getPyqTitle();
        mediaMessage.description = shareBean.getPyqDesc();
        new Thread(() -> {
            //构造一个req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = System.currentTimeMillis() + "";
            req.message = mediaMessage;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            iwxapi.sendReq(req);
        }).start();
    }

    private IWXAPI getWXApi() {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, ConstantsKt.APP_FOR_WX);
        iwxapi.registerApp(ConstantsKt.APP_FOR_WX);
        if (!iwxapi.isWXAppInstalled()) {
            if (onShareListener != null) {
                onShareListener.onError(context.getResources().getString(R.string.we_chat_not_install));
            }
            return null;
        }
        return iwxapi;
    }


    private static final String VIDEO_MINI_URL = "pages/video-player/index";
    private static final String USER_CENTER_URL = "pages/user-center/index";
    private static final String REPORT_URL = "pages/report-detail/index";

    public String getVideoMiniShareUrl() {
        return VIDEO_MINI_URL;
    }

    public String getUserCenterShareUrl() {
        return USER_CENTER_URL;
    }

    public String getReportShareUrl() {
        return REPORT_URL;
    }

    //小程序 分享到聊天 参数拼接
    private String urlJoint(String url, Map<String, Object> map) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (map == null) {
            return url;
        }
        if (map.size() == 0) {
            return url;
        }
        url += "?";
        StringBuilder urlBuilder = new StringBuilder(url);
        for (String key : map.keySet()) {
            urlBuilder.append(key);
            urlBuilder.append("=");
            urlBuilder.append(map.get(key));
            urlBuilder.append("&");
        }
        String result=urlBuilder.toString();
        if(result.endsWith("&")){
            result=result.substring(0,result.length()-1);
        }
        return result;
    }

    //小程序码 分享到朋友圈 参数拼接
    public String getMiniQrCodeJoint(Map<String, Object> map) {
        StringBuilder urlBuilder = new StringBuilder();
        if (map == null) {
            return urlBuilder.toString();
        }
        if (map.size() == 0) {
            return urlBuilder.toString();
        }

        for (String key : map.keySet()) {
            urlBuilder.append(key);
            urlBuilder.append("_");
            urlBuilder.append(map.get(key));
            urlBuilder.append("+");
        }
        return urlBuilder.toString();
    }

}
