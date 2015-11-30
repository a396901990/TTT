package com.dean.travltotibet.util;

import android.content.SharedPreferences;

import com.dean.travltotibet.TTTApplication;

/**
 * Created by DeanGuo on 10/7/15.
 * 记录工具
 */
public final class AppUtil {

    // 启动次数
    public static final String LAUNCH_COUNT = "launch_count";

    public static final String VERSION_DEFAULT = "0.0";

    public static final int VERSION_CODE_DEFAULT = 0;

    public static final String CURRENT_VERSION = "CURRENT_VERSION";

    public static final String CURRENT_VERSION_CODE = "CURRENT_VERSION_CODE";


    public static boolean isFirstLaunch() {
        SharedPreferences preferences = TTTApplication.getSharedPreferences();
        int launchCount = preferences.getInt(LAUNCH_COUNT, 0);
        if (launchCount == 0) {
            return true;
        }
        return false;
    }

    /**
     * 跟新启动次数 +1
     */
    public static void updateLaunchCount() {
        SharedPreferences preferences = TTTApplication.getSharedPreferences();
        int launchCount = preferences.getInt(LAUNCH_COUNT, 0);
        preferences.edit().putInt(LAUNCH_COUNT, ++launchCount).apply();
    }

    public static void saveVersionNumber(String verNum) {
        TTTApplication.getSharedPreferences().edit().putString(CURRENT_VERSION, verNum).commit();
    }

    public static void saveVersionCode(int verCode) {
        TTTApplication.getSharedPreferences().edit().putInt(CURRENT_VERSION_CODE, verCode).commit();
    }

    public static void getRemoteVersionCode(int verCode) {
        TTTApplication.getSharedPreferences().getInt(CURRENT_VERSION_CODE, VERSION_CODE_DEFAULT);
    }

    /**
     * @param argLocalVersion  Local version number
     * @param argRemoteVersion Remote version number
     * @return True if local < remote. False otherwise
     */
    public static boolean isNewVersion(final String argLocalVersion, final String argRemoteVersion) {
        float localVersion = 0;
        float remoteVersion = 0;

        try {
            localVersion = Float.parseFloat(argLocalVersion);
            remoteVersion = Float.parseFloat(argRemoteVersion);
        } catch (final NumberFormatException e) {
            SystemUtil.handleException(e);
        }

        return localVersion < remoteVersion ? true : false;
    }

}