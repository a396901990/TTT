package com.dean.travltotibet.activity;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.OrientationEventListener;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by DeanGuo on 11/6/15.
 */
public class SensorRotateActivity extends SlidingFragmentActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    OrientationEventListener sensorEvent;

    private enum SensorStateChangeActions {
        WATCH_FOR_LANDSCAPE_CHANGES, SWITCH_FROM_LANDSCAPE_TO_STANDARD, WATCH_FOR_POTRAIT_CHANGES, SWITCH_FROM_POTRAIT_TO_STANDARD;
    }

    private SensorStateChangeActions mSensorStateChanges;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void goFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        mSensorStateChanges = SensorStateChangeActions.WATCH_FOR_LANDSCAPE_CHANGES;

        if (null == sensorEvent)
            initialiseSensor(true);
        else
            sensorEvent.enable();
    }

    public void shrinkToPotraitMode() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorStateChanges = SensorStateChangeActions.WATCH_FOR_POTRAIT_CHANGES;
        if (null == sensorEvent)
            initialiseSensor(true);
        else
            sensorEvent.enable();
    }

    /**
     * Initialises system sensor to detect device orientation for player changes.
     * Don't enable sensor until playback starts on player
     *
     * @param enable if set, sensor will be enabled.
     */
    private void initialiseSensor(boolean enable) {
        sensorEvent = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
            /*
             * This logic is useful when user explicitly changes orientation using player controls, in which case orientation changes gives no callbacks.
             * we use sensor angle to anticipate orientation and make changes accordingly.
             */
                if (null != mSensorStateChanges
                        && mSensorStateChanges == SensorStateChangeActions.WATCH_FOR_LANDSCAPE_CHANGES
                        && ((orientation >= 60 && orientation <= 120) || (orientation >= 240 && orientation <= 300))) {
                    mSensorStateChanges = SensorStateChangeActions.SWITCH_FROM_LANDSCAPE_TO_STANDARD;
                } else if (null != mSensorStateChanges
                        && mSensorStateChanges == SensorStateChangeActions.SWITCH_FROM_LANDSCAPE_TO_STANDARD
                        && (orientation <= 40 || orientation >= 320)) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    mSensorStateChanges = null;
                    sensorEvent.disable();
                } else if (null != mSensorStateChanges
                        && mSensorStateChanges == SensorStateChangeActions.WATCH_FOR_POTRAIT_CHANGES
                        && ((orientation >= 300 && orientation <= 359) || (orientation >= 0 && orientation <= 45))) {
                    mSensorStateChanges = SensorStateChangeActions.SWITCH_FROM_POTRAIT_TO_STANDARD;
                } else if (null != mSensorStateChanges
                        && mSensorStateChanges == SensorStateChangeActions.SWITCH_FROM_POTRAIT_TO_STANDARD
                        && ((orientation <= 300 && orientation >= 240) || (orientation <= 130 && orientation >= 60))) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    mSensorStateChanges = null;
                    sensorEvent.disable();
                }
            }
        };
        if (enable)
            sensorEvent.enable();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            int orientation = getRequestedOrientation();
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                if (40.0f < event.values[2] && event.values[2] < 70.0f) {// 向左
                    if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                } else if (-10.0f < event.values[2] && event.values[2] < 10.0f) { // 正北
                    if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                } else if (-70.0f < event.values[2] && event.values[2] < -40.0f) { // 向右
                    if (orientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    }
                }
            } else {
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
