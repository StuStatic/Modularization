package com.example.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by adamchang on 2021/3/3.
 */
public class PhoneNumUtils {

    /**
     * 验证是否是正确合法的手机号码
     *
     * @param telephone
     *            需要验证的打手机号码
     * @return 合法返回true，不合法返回false
     * */
    public static boolean isPhoneNo(String telephone) {
        if (telephone == null || telephone.isEmpty()) {
            return false;
        }
        if (telephone.length() != 11) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[3,4,5,6,7,8,9]\\d{9}$");
        Matcher matcher = pattern.matcher(telephone);

        if (matcher.matches()) {
            return true;
        }
        return false;

    }
}
