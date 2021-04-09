package com.example.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import androidx.annotation.NonNull;

import com.example.common.bean.FilePathBean;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * fileName：FileUtil
 *
 * @author ：qyh
 * date    ：2019-11-28 17:43
 * describe：
 */
public class FileUtil {

    /**
     * 获取文件目录下所有文件夹
     *
     * @param fileAbsolutePath
     * @return
     */
    public static ArrayList<FilePathBean> getFileFolderList(String fileAbsolutePath) {
        return getFileOrFolderList(fileAbsolutePath, false);
    }

    /**
     * 获取指定目录所有文件
     *
     * @param fileAbsolutePath
     * @return
     */
    public static ArrayList<FilePathBean> getFileList(String fileAbsolutePath) {
        return getFileOrFolderList(fileAbsolutePath, true);
    }

    /**
     * @param fileAbsolutePath
     * @param isFileFolder     是否是文件夹
     * @return
     */
    private static ArrayList<FilePathBean> getFileOrFolderList(String fileAbsolutePath,
                                                               boolean isFileFolder) {
        ArrayList<FilePathBean> filePathLists = new ArrayList<>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if (null == subFile || subFile.length <= 0) {
            return filePathLists;
        }
        // 一：遍历文件夹中所以得图片，存入集合中，这时顺序可能不对
        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是文件夹还是文件
            if (subFile[iFileLength].isFile() == isFileFolder) {
                FilePathBean filePathBean = new FilePathBean();
                // 格式：storage/emulated/0/Android/data/com.yiqi.onetoone
                // .teacher/cache/guide_photo_dir/1567155311055_1.png
                String filePath = subFile[iFileLength].getAbsolutePath() + "/";
                String fileName = subFile[iFileLength].getName();
                filePathBean.setPath(filePath);
                filePathBean.setName(fileName);
                // 格式：1567155311055_1.png
                filePathLists.add(filePathBean);
            }
        }

        return filePathLists;
    }

    public static String getVideoPath(Context context) {
        String path =Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/DCIM/Camera/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }


    /***
     * 获取缓存路径
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        File sdDir = context.getExternalCacheDir();
        if (sdDir == null) {
            sdDir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/data/" + context.getPackageName() + "/cache");
            sdDir.mkdirs();
        }
        return sdDir.getPath();
    }

    /**
     * 存储课程下载地址
     *
     * @param context
     * @return
     */
    public static String getCourseSavePath(Context context) {
        return getCacheDir(context) + "/course/";
    }

    /**
     * 存储学习报告地址
     *
     * @param context
     * @return
     */
    public static String getStudySavePath(Context context) {
        return getCacheDir(context) + "/image/";
    }


    /**
     * 存储相机照片地址
     *
     * @param context
     * @return
     */
    public static String getPhotoSavePath(Context context) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    /**
     * 视频第一帧保存地址
     * @param context
     * @return
     */
    public static String getPictureSavePath(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        } else {
            return getCacheDir(context) + "/picture/";
        }
    }

    /**
     * 存储绘画理念地址
     *
     * @param context
     * @return
     */
    public static String getAudioSavePath(Context context) {
        return getCacheDir(context) + "/recorder/";
    }


    public static String getCourseResourcePath(Context context) {
        return getCacheDir(context) + "/resource/";
    }

    /**
     * 获取res/raw路径
     *
     * @param context
     * @return
     */
    public static String getRawPath(Context context) {
        return "android.resource://" + context.getPackageName() + "/";
    }

    public static File uriToFile(Uri uri) {
        File file = null;
        try {
            file = new File(new URI(uri.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static boolean makeDirs(@NonNull String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static String getFolderName(@NonNull String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }
}
