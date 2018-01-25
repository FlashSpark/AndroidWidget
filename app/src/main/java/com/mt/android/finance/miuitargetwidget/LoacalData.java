package com.mt.android.finance.miuitargetwidget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 缓存文本
 * author:ps
 * date:2017/12/27
 */

public class LoacalData {

    // 打印中信息操作类
    private static final String SYSTEM_SHARED_PREFERENCE = "widget";
    private static final String KEY_DAY = "content_day";
    private static final String KEY_NIGHT = "content_night";
    private static final String KEY_STATUS = "status_day_night";

    public static String loadDayContent(Context context) {
        String content = "";
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            content = sharedPreferences.getString(KEY_DAY, "");
        }
        return content;
    }

    public static String loadNightContent(Context context) {
        String content = "";
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            content = sharedPreferences.getString(KEY_NIGHT, "");
        }
        return content;
    }

    public static void saveDayContent(Context context, String info) {
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_DAY, info).apply();
        }
    }

    public static void saveNightContent(Context context, String info) {
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_DAY, info).apply();
        }
    }

    public static void saveStatusContent(Context context, boolean stauts) {
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_STATUS, stauts).apply();
        }
    }

    public static boolean isDayStatus(Context context) {
        boolean status = true;
        if (null != context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SYSTEM_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            status = sharedPreferences.getBoolean(KEY_STATUS, true);
        }
        return status;
    }
}

