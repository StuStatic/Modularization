package com.example.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.example.common.bean.FilePathBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * fileName：CommonUtil
 *
 * @author ：qyh
 * date    ：2019-11-29 18:16
 * describe：
 */
public class CommonUtil {

    private static String defultString;

    /**
     * 获取随机数
     *
     * @param maxNum 范围：0-maxNum-1
     * @return
     */
    public static int getRandomNum(int maxNum) {
        Random random = new Random();
        return random.nextInt(maxNum);
    }

    /**
     * 将前端返回的URL解析成Map
     *
     * @param url 类似 bearmusicapp://musicBear?type=open&page=mycourse 打开我的课程
     *            或者：bearmusicapp://musicBear?type=fun&method=showShareButton&isShow=1&json={}
     * @return
     */
    public static Map<String, String> getParameterMap(String url) {

        Map<String, String> stringObjectMap = new HashMap<>();
        try {
            int firstPosition = url.indexOf("?");
            if (firstPosition >= 0) {
                String substring = url.substring(firstPosition + 1);
                defultString = substring;
                if (substring.contains("json")) {
                    String[] splitString = substring.split("json=");
                    defultString = splitString[0];
                    String jsonString = splitString[1];
                    stringObjectMap.put("json", jsonString);
                }
                String[] split = defultString.split("&");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    String[] split1 = s.split("=");
                    stringObjectMap.put(split1[0], split1[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringObjectMap;
    }

    /**
     * 判断一个字符是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumericzidai(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否需要下载
     *
     * @param context
     * @param fileFolderZipName   xx.zip
     * @return
     */
    public static String isClassNeedDownload(Context context, String fileFolderZipName) {
        String courseFolderName = replaceZip(fileFolderZipName);
        String cacheDir = FileUtil.getCourseSavePath(context);
        ArrayList<FilePathBean> list = FileUtil.getFileFolderList(cacheDir);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                FilePathBean filePathBean = list.get(i);
                LoggerUtil.e("courseName==="+courseFolderName+"==getName===="+filePathBean.getName());
                if (filePathBean != null && courseFolderName.equals(filePathBean.getName())) {
                    return filePathBean.getPath();
                }
            }
        }
        return null;
    }

    /**
     * 替换String 中.zip，如果s1t2.zip =>s1t2
     * @param text
     * @return
     */
    public static String  replaceZip(String text){
        if (!TextUtils.isEmpty(text) && text.contains(".zip")) {
            String courseName = text.replace(".zip", "");
            return courseName;
        }
        return text;
    }

    /**
     * 判断传入的【文件夹】在这个目录下是否存在是否存在
     *
     * @return
     */
    public static boolean isFileFolderExit(String savePath,String fileFolderZipName) {
        String courseFolderName = replaceZip(fileFolderZipName);
        ArrayList<FilePathBean> fileList = FileUtil.getFileFolderList(savePath);
        LoggerUtil.e("fileList===" + fileList.toString());
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                FilePathBean filePathBean = fileList.get(i);
                if (filePathBean != null && courseFolderName.equals(filePathBean.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断传入的【文件】在这个目录下是否存在是否存在
     *
     * @return
     */
    public static boolean isFileExit(String savePath,String fileName) {
        ArrayList<FilePathBean> fileList = FileUtil.getFileList(savePath);
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                FilePathBean filePathBean = fileList.get(i);
                if (filePathBean != null && fileName.equals(filePathBean.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 截取课程名字
     *
     * @param fileName
     * @return
     */
    public static String getCourseName(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            String[] split = fileName.split("/");
            if (split != null && split.length > 0) {
                for (int i = split.length - 1; i >= 0; i--) {
                    if (split[i].contains(".zip")) {
                        return split[i];
                    }
                }
                return null;
            }
        }
        return null;
    }
}
