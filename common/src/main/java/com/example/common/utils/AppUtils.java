package com.example.common.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresPermission;

import com.example.common.constants.ConstantsKt;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 根据scheme 判断是否需要app来处理
     *
     * @param url
     * @return
     */
    public static boolean isApp(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.trim().startsWith("bearmusicapp");
    }

    /**
     * 获取应用程序包名
     *
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本号]
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 检测指定的App是否已安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    /**
     * 跳到系统设置中的应用详情
     *
     * @param context Context
     */
    public static void gotoAppDetail(Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getAppPackageName(context), null);
        intent.setData(uri);
        if (isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 检测 Intent 是否可用
     *
     * @param mContext Context
     * @param intent   Intent
     * @return
     */
    public static boolean isIntentAvailable(Context mContext, Intent intent) {
        PackageManager packageManager = mContext.getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }


    /**
     * 读取CHANNEL的值
     */
    public static String getChannel(Context context) {
        ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return Objects.requireNonNull(info.metaData.get("UMENG_CHANNEL")).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "获取渠道失败";
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取APP版本名称
     *
     * @return
     */
    public static String getAppVersionName(Context context) {
        String appVersion = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageInfo(getPackageName(context), 0);
            appVersion = pkgInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    /**
     * 获取APP版本名称
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int appCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pkgInfo = pm.getPackageInfo(getPackageName(context), 0);
            appCode = pkgInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appCode;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static String generateUniqueDeviceId(Context context) {
        String result = null;
        String imei = "ImConstants.imei";
        String androidId = "";
        String macAddress = "";
        ContentResolver contentResolver = context.getContentResolver();
        if (contentResolver != null) {
            androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            macAddress = wifiManager.getConnectionInfo().getMacAddress();
        }

        StringBuilder longIdBuilder = new StringBuilder();
        if (imei != null) {
            longIdBuilder.append(imei);
        }
        if (androidId != null) {
            longIdBuilder.append(androidId);
        }
        if (macAddress != null) {
            longIdBuilder.append(macAddress);
        }
        //唯一标识，加上包名
        if (!TextUtils.isEmpty(ConstantsKt.APPLICATION_ID)) {
            longIdBuilder.append(ConstantsKt.APPLICATION_ID);
        }
        if (TextUtils.isEmpty(longIdBuilder.toString())) {
            //生成一个唯一的uuid
            result = MD5.toMd5(UUID.randomUUID().toString() + ConstantsKt.APPLICATION_ID);
        } else {
            result = MD5.toMd5(longIdBuilder.toString());
        }
        return String.valueOf(result.hashCode());
    }

    /**
     * 获取手机的IMEI地址
     *
     * @return null - 不存在或失败
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getPhoneImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) return null;
        if (tm.getDeviceId() == null || tm.getDeviceId().equals("")) {
            return getWifiMac(context);
        }
        return tm.getDeviceId();
    }

    /**
     * 获取WIFI的MAC地址
     *
     * @return null - 不存在或失败
     */
    private static String getWifiMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return null;
        }
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return null;
        }
        return info.getMacAddress();
    }

    /**
     * 隐藏软键盘
     *
     * @param context 当前上下文
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        View v = ((Activity) context).getWindow().peekDecorView();
        if (null != v && imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    /**
     * 显示软键盘
     *
     * @param context 当前上下文
     */
    public static void showKeyboard(View view,Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (null != view && imm != null) {
            imm.showSoftInput(view, 0);
        }
    }
    /**
     * 显示软键盘
     *
     * @param context 当前上下文
     */
    public static void toggleKeyboard(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void judeKeyboard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if(imm==null){
            return;
        }
        if(view==null){
            return;
        }
        if(!imm.isActive(view)){
            return;
        }
        showKeyboard(view,context);
    }

    public static void back(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        try {
            Intent mainIntent = new Intent("com.msb.music.ui.ContainerActivity");
            ActivityManager am = (ActivityManager) activity.getSystemService(Activity.ACTIVITY_SERVICE);
            if (am == null) {
                activity.finish();
                return;
            }
            List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
            if (appTask.size() <= 0) {
                if (activity.getIntent().getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK) {
                    activity.startActivity(mainIntent);
                }
                activity.finish();
                return;
            }
            if (appTask.get(0).baseActivity.equals(activity.getIntent().getComponent())) {
                activity.startActivity(mainIntent);
            } else if (appTask.get(0).baseActivity.toString().contains("launcher.Launcher")) {
                activity.startActivity(mainIntent);
            }
            activity.finish();
        } catch (Throwable e) {
            e.printStackTrace();
            activity.finish();
        }
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        return generateUniqueDeviceId(context);
    }

    /**
     * 剪切板，复制公众号或微信号
     */
    public static void copyToClipboard(Context context, CharSequence content) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, content));//参数一：标签，可为空，参数二：要复制到剪贴板的文本
            if (clipboard.hasPrimaryClip()) {
                clipboard.getPrimaryClip().getItemAt(0).getText();
            }
        }
    }


    /**
     * 检测是否安装支付宝
     *
     * @return * @param context
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;

    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvaolable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验某个服务是否还存在
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        // 校验服务是否还存在
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo info : services) {
            // 得到所有正在运行的服务的名称
            String name = info.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 根据key获取manifest里配置的metadata数据
     *
     * @param context
     * @param key
     * @return
     */
    public static String getMetaDataValue(Context context, String key) {
        ApplicationInfo ai = null;
        String value = "";
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getString(key);
        } catch (Exception e) {

        }
        return value;
    }

    public static String getAppChannel(Context context) {
        ApplicationInfo ai = null;
        String value = "";
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = ai.metaData.getString("CHANNEL", "");
        } catch (Exception e){

        }
        return value;
    }
}