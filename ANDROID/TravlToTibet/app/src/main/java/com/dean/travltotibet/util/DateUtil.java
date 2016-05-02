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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DeanGuo on 1/23/16.
 */
public final class DateUtil {

    public static final String YYYY = "yyyy";

    public static final String M = "M";

    public static final String MONTH_MARK = "-%s-";

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

//            int year= (int) Math.floor(l / (24 * 60 * 60 * 1000 * 365));

            int day= (int) (l/(24*60*60*1000));

            int hour= (int) (l/(60*60*1000)-day*24);

            int min= (int) ((l/(60*1000))-day*24*60-hour*60);

            int s= (int) (l/1000-day*24*60*60-hour*60*60-min*60);

//            Log.e("时间相差：", year+"年"+day + "天" + hour + "小时" + min + "分钟" + s + "秒。");

            if (day > 365) {
                timeGap = Math.floor(day/365)+"年前";
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
                timeGap = "刚刚";
            }

            return timeGap;
        }
        catch (final ParseException ex)
        {
            return null;
        }
    }

    /**
    * 计算两个日期之间相差的天数
    * @param smdate 较小的时间
    * @param bdate  较大的时间
    * @return 相差天数
    * @throws ParseException
    */
    public static int daysBetween(Date smdate,Date bdate)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate=sdf.parse(sdf.format(smdate));
            bdate=sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=Math.abs( (time2-time1) / (1000*3600*24) );

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static ArrayList<String> getExpiredYears() {
        ArrayList<String> yeas = new ArrayList<>();
        int curYear = Integer.parseInt(getCurrentTimeFormat(YYYY));
        // cur = 2016 : 2015 2014 2013 2012 2011
        for (int i= curYear-1; i > curYear-5; i--) {
            yeas.add(String.valueOf(i));
            Log.e("eyear ", i +"");
        }
        return yeas;
    }

    public static int getCurYear() {
        return Integer.parseInt(getCurrentTimeFormat(YYYY));
    }

    public static String getNextYear() {
        return String.valueOf(getCurYear() + 1);
    }

    public static String getSelectedDate(String date) {
        // 5月：5
        String newDate = date.split("月")[0];

        // 5 : -5-
        return String.format(DateUtil.MONTH_MARK, newDate );
    }

    public static boolean isSameYear(Date d1, Date d2) {
        String y1 = formatDate(d1, DateUtil.YYYY);
        String y2 = formatDate(d2, DateUtil.YYYY);

        return y1.equals(y2);
    }
}
