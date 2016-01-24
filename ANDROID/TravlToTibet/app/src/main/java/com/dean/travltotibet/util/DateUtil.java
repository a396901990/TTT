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

}
