package com.example.common.utils;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Log管理工具，上线前设置 debugFlag = false。
 */
public class LoggerUtil {
    public static boolean debugFlag = true;
    private static final String LOGTAG = "lb";
    // 根据需要将Log存放到SD卡中
    private static String path;
    private static FileOutputStream outputStream;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void i(String msg) {
        if (debugFlag)
            Log.i(LOGTAG, msg);
    }

    public static void i(String key, String msg) {
        if (debugFlag)
            Log.i(key, msg);
    }

    public static void e(String key, String msg) {
        if (debugFlag)
            Log.e(key, msg);
    }

    public static void d(String msg) {
        if (debugFlag)
            Log.d(LOGTAG, msg);
    }

    public static void d(String key, String msg) {
        if (debugFlag)
            Log.d(key, msg);
    }

    public static void e(String msg) {
        if (debugFlag)
            Log.e(LOGTAG, msg);
    }

    /**
     * 打印出来Json信息；使用默认tag
     *
     * @param jsonMsg
     */
    public static void printJson(String jsonMsg) {
        printJsonWithHead(LOGTAG, jsonMsg, "");
    }

    /**
     * 打印出来Json信息；使用自定义tag
     *
     * @param jsonMsg
     */
    public static void printJson(String tag, String jsonMsg) {
        printJsonWithHead(tag, jsonMsg, "");
    }

    /**
     * 打印出来Json信息；使用自定义tag，并且添加Head信息。
     *
     * @param tag
     * @param msg
     * @param headString
     */
    public static void printJsonWithHead(String tag, String msg, String headString) {
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.i(tag, "║ " + line);
        }
        printLine(tag, false);
    }


    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            //Log.i(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            //Log.i(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    static {
        if (LoggerUtil.debugFlag) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/MsbLog/";
                File file_Path = new File(path);
                file_Path.mkdirs();
                File file = new File(path, "log.txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    outputStream = new FileOutputStream(file, true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 将错误信息保存到SD卡中去！可选的操作！
    @SuppressWarnings("deprecation")
    public static void save2Sd(String msg) {
        Date date = new Date();
        String time = date.toLocaleString();
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            if (outputStream != null) {
                try {
                    outputStream.write(time.getBytes());
                    outputStream.write(msg.getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
