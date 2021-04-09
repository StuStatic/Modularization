package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jindan
 *         MD5处理类
 */
public class MD5 {
    public static String toMd5(String md5Str) {
        String result = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(md5Str.getBytes("utf-8"));
            result = toHexString(algorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成16位md5码
     * @param md5Str
     * @return
     */
    public static String to16bitMd5(String md5Str) {
        String result = "";
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(md5Str.getBytes("utf-8"));
            result = toHexString(algorithm.digest()).substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int b : bytes) {
            if (b < 0) {
                b += 256;
            }
            if (b < 16) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(b));
        }
        return hexString.toString();
    }
}
