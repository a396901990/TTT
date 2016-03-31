package com.dean.travltotibet.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;

import com.dean.travltotibet.BuildConfig;
import com.dean.travltotibet.TTTApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DeanGuo on 11/7/15.
 */
public final class SystemUtil {
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    public static String getAppVersion( Context context )
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static int getAppVersionCode( Context context )
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String[] getStringArray( Context context, int id )
    {

        String[] array = null;
        try
        {
            array = context.getResources().getStringArray(id);
        }
        catch (Resources.NotFoundException e)
        {
            array = null;
            SystemUtil.handleException(e);
        }
        return array;
    }

    /**
     * handle exception
     *
     * @param e
     */
    public static void handleException( Exception e )
    {
        if (BuildConfig.DEBUG)
        {
            Log.e("SystemUtil", e.getMessage(), e);
        }
    }

    /**
     * Method to check if we have a network connection or not
     *
     * @return true if network available
     * */
    public static boolean isOnline( final Context argContext )
    {
        boolean isOnline = false;

        // Get the ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) argContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for any NetworkInfo
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        // Can be null if no network detected
        if (netInfo != null)
        {
            isOnline = netInfo.isConnectedOrConnecting();
        }

        // Return true if connected; false otherwise
        return isOnline;
    }

    // 获取手机状态栏高度
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = TTTApplication.getMyResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    // 获取ActionBar的高度
    public int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (TTTApplication.getMyTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))// 如果资源是存在的、有效的
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, TTTApplication.getMyResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static void createTempFile() {
        File file=new File(getMyPicPath());
        if(!file.exists())
            file.mkdir();
    }


    public static void delTempFile() {

        File file=new File(getMyPicPath());
        if(!file.exists())
            file.delete();
    }

    public static String getMyPicPath() {
        File sd=Environment.getExternalStorageDirectory();
        return sd.getPath()+"/tanzi";
    }
}
