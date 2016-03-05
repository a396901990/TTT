package com.dean.travltotibet.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;

import com.dean.travltotibet.BuildConfig;
import com.dean.travltotibet.TTTApplication;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DeanGuo on 1/23/16.
 */
public final class DateUtil {

    public static String getCurrentTimeFormat(String argFormat) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(argFormat);
        return sdf.format(date);
    }

    public static String formatDate( final Date argDate, final String argFormat )
    {
        SimpleDateFormat formatter = new SimpleDateFormat(argFormat, Locale.CHINA);
        return formatter.format(argDate);
    }

    public static Date parse( final String argDateString, final String argFormat )
    {
        SimpleDateFormat formatter = new SimpleDateFormat(argFormat, Locale.CHINA);
        try
        {
            return formatter.parse(argDateString);
        }
        catch (final ParseException ex)
        {
            return null;
        }
    }

    public static String getTimeGap( final String argDateString, final String argFormat )
    {
        String timeGap = null;
        SimpleDateFormat formatter = new SimpleDateFormat(argFormat, Locale.CHINA);
        try
        {
            Date targetTime = formatter.parse(argDateString);
            Date nowTime = new Date();
            long l=nowTime.getTime()-targetTime.getTime();

            int year= (int) (l/(24*60*60*1000*365));

            int day= (int) (l/(24*60*60*1000));

            int hour= (int) (l/(60*60*1000)-day*24);

            int min= (int) ((l/(60*1000))-day*24*60-hour*60);

            int s= (int) (l/1000-day*24*60*60-hour*60*60-min*60);

//            Log.e("时间相差：", day + "天" + hour + "小时" + min + "分钟" + s + "秒。");

            if (year > 0) {
                timeGap = year+"年前";
            }
            else if (day > 0) {
                timeGap = day+"天前";
            }
            else if (hour > 0) {
                timeGap = hour+"小时前";
            }
            else if (min > 0) {
                timeGap = min+"分钟前";
            }
            else if (s > 0) {
                timeGap = "刚刚";
            }
            else if (l < 0) {
                timeGap = argDateString;
            }

            return timeGap;
        }
        catch (final ParseException ex)
        {
            return null;
        }
    }

}
