package com.example.common.utils;

import android.text.TextUtils;

/**
 * Created by ChengJinbao
 * 2019年11月16日
 */
public class StringsUtil {
    public static String formatPhone(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charSequence.length(); i++) {
            if (i != 3 && i != 8 && charSequence.charAt(i) == ' ') {
                continue;
            } else {
                stringBuilder.append(charSequence.charAt(i));
                if ((stringBuilder.length() == 4 || stringBuilder.length() == 9)
                        && stringBuilder.charAt(stringBuilder.length() - 1) != ' ') {
                    stringBuilder.insert(stringBuilder.length() - 1, ' ');
                }
            }
        }
        return stringBuilder.toString();
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    public static String resetWebUrl(String link, String grouponId, String sendId) {
        String channel = "channelId=";
        String url;
        if (!link.contains("sendId")) {
            if (link.contains("?")) {
                url = link + "&sendId=" + sendId;
            } else {
                url = link + "?sendId=" + sendId;
            }
        } else {
            url = link;
        }
        // 将URL中channelId=xxx替换成channelId=BuildConfig.CHANNEL_ID_SHARE
        if (url.contains(channel)) {
            int firstPosition = url.indexOf(channel);
            if (firstPosition >= 0) {
                StringBuilder sb = new StringBuilder();
                String substring = url.substring(firstPosition + channel.length());
                for (int i = 0; i < substring.length(); i++) {
                    String substring1 = substring.substring(i, i + 1);
                    if (CommonUtil.isNumericzidai(substring1)) {
                        sb.append(substring1);
                    } else {
                        break;
                    }
                }
                url = url.replace(channel + sb.toString(), channel + "5");
            }
        } else {
            url += "&channelId=" + "5";
        }
        if (!TextUtils.isEmpty(grouponId)) {
            url += "&grouponId=" + grouponId;
        }
        return url;
    }

    /**
     * 手机中间四位显示****
     * @param mobile
     * @return
     */
    public static String getStarMobile(String mobile) {
        if (!TextUtils.isEmpty(mobile)) {
            if (mobile.length() >= 11)
                return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        } else {
            return "";
        }
        return mobile;
    }

}