package com.dean.travltotibet.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.SystemUtil;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;


/**
 * Created by DeanGuo on 11/7/15.
 */
public class AppLaunchActivity extends Activity {

    private String currentAppVersion = AppUtil.VERSION_DEFAULT;

    private final static String BMOB_APPLICATION_ID = "1ac4c82c189eb0d80711885ed3ad05ba";

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    static final int REQUEST_WELCOME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // 初始化Bmob
        Bmob.initialize(this, BMOB_APPLICATION_ID);
        BmobUpdateAgent.initAppVersion(getApplication());

        // 跟新启动次数
        AppUtil.updateLaunchCount();

        // 获取当前app版本
        this.currentAppVersion = SystemUtil.getAppVersion(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!WelcomeActivity.hasShown(currentAppVersion)) {
                    goToWhatsNew();
                } else {
                    goToHome();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    /**
     * 进入home界面
     */
    public void goToHome() {
        Intent intent = new Intent(getApplication(), HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    public void goToWhatsNew() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult(intent, REQUEST_WELCOME);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    private void persistConfigurationData() {
        SharedPreferences sp = TTTApplication.getSharedPreferences();
        String[] default_points = getResources().getStringArray(R.array.default_points);
        StringBuffer sb = new StringBuffer();
        for (String point : default_points) {
            sb.append(point);
            sb.append(Constants.POINT_DIVIDE_MARK);
        }

        sp.edit().putString(Constants.CURRENT_POINTS, sb.toString()).commit();
    }


    @Override
    protected void onResume() {
        // 注册event bus
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 注销event bus
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_WELCOME) {
            if (resultCode == RESULT_OK) {
                goToHome();
            } else {
                finish();
            }
        }
    }
}
