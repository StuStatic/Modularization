package com.example.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by ChengJinbao
 * 2019年12月20日
 */
public class SDCardUtils {

    private String sdPath;
    private int progress;

    private SDCardUtils() {
        sdPath = Environment.getExternalStorageDirectory() + "/bearmusicapp/";
    }

    private final static class SDCardUtilsHolder {
        private final static SDCardUtils INSTANCE = new SDCardUtils();
    }

    public static SDCardUtils getInstance() {
        return SDCardUtilsHolder.INSTANCE;
    }

    public String getSdPath() {
        return sdPath;
    }

    public File createNewFile(String fileName) throws IOException {
        File file = new File(sdPath + fileName);
        file.createNewFile();
        return file;
    }

    public File createSDDir(String dirName) {
        File dir = new File(sdPath + dirName);
        dir.mkdir();
        return dir;
    }

    public boolean isFiisFileExistleExist(String fileName) {
        File file = new File(sdPath + fileName);
        return file.exists();
    }

    /**
     * 格式化空间大小，为MB
     *
     * @param byteSpace 待格式的空间大小，单位为字节。
     * @return
     */
    private long formatSpace2M(long byteSpace) {
        return byteSpace / 1000 / 1000;
    }

    /**
     * 获取内部存储，总空间，单位MB
     *
     * @return
     */
    private long getTotalDisk() {
        File dir = Environment.getDataDirectory();
        StatFs stat = new StatFs(dir.getPath());

        return formatSpace2M(stat.getBlockSizeLong() * stat.getBlockCountLong());
    }

    /**
     * 获取内部存储，剩余空间，单位MB
     *
     * @return
     */
    public long getFreeDisk() {
        File dir = Environment.getDataDirectory();
        StatFs stat = new StatFs(dir.getPath());
        return formatSpace2M(stat.getBlockSizeLong() * stat.getAvailableBlocksLong());
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; files != null && i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        return dirFile.delete();
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    private long getZipSize(String filePath, IProgress iProgress) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            LoggerUtil.e("获取压缩包大小异常====" + e.getMessage());
            if (iProgress != null) {
                iProgress.onError("获取压缩包大小异常");
            }
            reportException(e);
            size = 0;
        }
        LoggerUtil.e("获取压缩包大小====" + size);
        return size;
    }


    public void unZip(String filePath, String outPath, IProgress iProgress) {
        File zipFile = new File(filePath);
        if (zipFile == null || !zipFile.exists()) {
            iProgress.onError("zip不存在 " + filePath);
            return;
        }

        boolean isRoot = true;
        long zipLength = getZipSize(filePath, iProgress);
        ZipInputStream zipInputStream = null;

        try {
            zipInputStream = new ZipInputStream(new FileInputStream(filePath));
            if (zipInputStream == null || zipInputStream.available() == 0) {
                iProgress.onError("zip错误");
                return;
            }

            ZipEntry zipEntry;
            String szName;
            String rootFileName = null;
            String tempRootFileName = null;
            File tempRootFile = null;
            long count = 0;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                szName = zipEntry.getName();
                String currentParentFile;
                if (zipEntry.isDirectory()) {
                    szName = szName.substring(0, szName.length() - 1);
                    if (isRoot) {
                        rootFileName = szName;
                        tempRootFileName = rootFileName + "_temp";
                        currentParentFile = outPath + File.separator + tempRootFileName;
                        tempRootFile = new File(currentParentFile);
                        tempRootFile.mkdirs();
                        isRoot = false;
                    } else {
                        String tempSzName = szName.replace(rootFileName, tempRootFileName);
                        currentParentFile = outPath + File.separator + tempSzName;
                        new File(currentParentFile).mkdirs();
                    }

                } else {
                    String tempSzName = szName.replace(rootFileName, tempRootFileName);
                    File file = new File(outPath + "/" + tempSzName);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    // 获取文件的输出流
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024 * 2];
                    // 读取（字节）字节到缓冲区
                    while ((len = zipInputStream.read(buffer)) != -1) {
                        count += len;
                        if (zipLength > 0) {
                            progress = (int) ((count * 100) / zipLength);
                            iProgress.onProgress(progress);
                        }
                        // 从缓冲区（0）位置写入（字节）字节
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            }
            if (iProgress != null) {
                if (progress == 100) {
                    // 解压完成 重命名
                    File realFile = new File(outPath + File.separator + rootFileName);
                    tempRootFile.renameTo(realFile);
                    iProgress.onDone();
                } else {
                    LoggerUtil.e("解压失败进度不到100===");
                    iProgress.onError("");
                }
            }
        } catch (Exception exc) {
            reportException(exc);
            if (iProgress != null) {
                LoggerUtil.e("解压失败===" + exc.getMessage());
                iProgress.onError(exc.getMessage());
            }
        } finally {
            try {
                zipInputStream.close();
                // FileUtil.getInstance().deleteFile(filePath);
            } catch (IOException e) {
                reportException(e);
                LoggerUtil.e("解压失败进度不到===" + e.getMessage());
                if (iProgress != null) {
                    iProgress.onError(e.getMessage());
                }
            }
        }
    }


    public interface IProgress {
        void onProgress(int progress);

        void onError(String msg);

        void onDone();
    }

    private void reportException(Exception e){
//        CrashReport.postCatchedException(e);
    }

}
