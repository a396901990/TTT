/**
 * Copyright 2014, FMR LLC.
 * All Rights Reserved.
 * Fidelity Confidential Information
 */
package com.dean.travltotibet.util;

import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;

public class CompatHelper {

    @SuppressLint("NewApi")
    public static boolean getUserVisibleHint(final Fragment frag) {
        if (android.os.Build.VERSION.SDK_INT >= 15) {
            return frag.getUserVisibleHint();
        }

        return true;
    }

    @SuppressWarnings("ResourceType")
    @SuppressLint("InlinedApi")
    public static int lockRotation(Activity activity) {
        int origin = activity.getRequestedOrientation();
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        } else {
            activity.setRequestedOrientation(getActivityRotationInfo(activity));
        }
        return origin;
    }

    public static void rotateScreen(Activity activity) {
        int origin = getActivityRotationInfo(activity);

        if (origin == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else if (origin == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    public static boolean isOrientationLocked(Activity activity) {
        switch (activity.getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LOCKED:
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT:
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
            case ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE:
                return true;

        }

        return false;
    }

    public static int getActivityRotationInfo(Activity activity) {
        int displayRotation = getDisplayRotation(activity);
        // Display rotation >= 180 means we need to use the REVERSE landscape/portrait
        boolean standard = displayRotation < 180;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return standard ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        }

        if (displayRotation == 90 || displayRotation == 270) {
            // If displayRotation = 90 or 270 then we are on a landscape
            // device. On landscape devices, portrait is a 90 degree
            // clockwise rotation from landscape, so we need
            // to flip which portrait we pick as display rotation is counter clockwise
            standard = !standard;
        }
        return standard ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
    }

    private static int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

}
