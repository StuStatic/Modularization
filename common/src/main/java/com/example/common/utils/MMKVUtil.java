package com.example.common.utils;


import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;


/**
 * Created by ChengJinbao
 * 2019年11月11日
 */
public class MMKVUtil {

    private MMKVUtil() {
    }

    public static MMKVUtil getInstance() {
        return MMVKUtilHolder.INSTANCE;
    }

    /**
     * 初始MMKV
     *
     * @param context
     */
    public void initMMKV(Context context) {
        String result = MMKV.initialize(context);
        Log.d("mmkv root", "mmkv root: " + result);
    }

    private MMKV getMMKV() {
        return MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null);
    }

    public void putString(@NonNull String key, String value) {
        getMMKV().encode(key, value);
    }

    public String getString(@NonNull String key) {
        return getString(key, "");
    }

    public String getString(@NonNull String key, String defaultKey) {
        return getMMKV().decodeString(key, defaultKey);
    }

    public void putBoolean(@NonNull String key, boolean value) {
        getMMKV().encode(key, value);
    }

    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return getMMKV().decodeBool(key, defaultValue);
    }

    public void putInt(@NonNull String key, int value) {
        getMMKV().encode(key, value);
    }

    public int getInt(@NonNull String key) {
        return getInt(key, 0);
    }

    public int getInt(@NonNull String key, int defaultInt) {
        return getMMKV().decodeInt(key, defaultInt);
    }

    public void putLong(@NonNull String key, long value) {
        getMMKV().encode(key, value);
    }

    public long getLong(@NonNull String key) {
        return getLong(key, 0L);
    }

    public long getLong(@NonNull String key, long defaultLong) {
        return getMMKV().decodeLong(key, defaultLong);
    }

    public void putParcelable(@NonNull String key, Parcelable value) {
        getMMKV().encode(key, value);
    }

    public <T extends Parcelable> T getParcelable(@NonNull String key, Class<T> parcelableClass) {
        return getMMKV().decodeParcelable(key, parcelableClass);
    }

    public boolean containKey(@NonNull String key) {
        return getMMKV().containsKey(key);
    }

    public void removeKey(@NonNull String key) {
        if (containKey(key)) {
            getMMKV().removeValueForKey(key);
        }
    }

    public void removeKeys(@NonNull String... key) {
        getMMKV().removeValuesForKeys(key);
    }

    public void clearAll() {
        getMMKV().clearAll();
    }
    public void clearString(String key) {
        getMMKV().putString(key,null);
    }
    private static class MMVKUtilHolder {
        private static final MMKVUtil INSTANCE = new MMKVUtil();
    }
}
