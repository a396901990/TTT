package com.dean.travltotibet.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.RecordUtil;
import com.dean.travltotibet.util.SystemUtil;


/**
 * Created by DeanGuo on 11/7/15.
 */
public class AppLaunchActivity extends Activity {

    private String currentAppVersion = RecordUtil.VERSION_DEFAULT;

    private String remoteAppVersion = RecordUtil.VERSION_DEFAULT;

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    static final int REQUEST_WHATSNEW = 0;

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

        // 跟新启动次数
        RecordUtil.updateLaunchCount();

        // 获取当前app版本
        this.currentAppVersion = SystemUtil.getAppVersion(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                remoteAppVersion = "2.0";

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

        // 有新版本
        if (RecordUtil.isNewVersion(currentAppVersion, remoteAppVersion)){
            intent.putExtra(Constants.APP_LAUNCH_VERSION, remoteAppVersion);
        }
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    public void goToWhatsNew() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult(intent, REQUEST_WHATSNEW);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    public void checkUpdate() {

        // 获取app version number
        String remoteAppVersion = "?";
        String currentAppVersion = TTTApplication.getSharedPreferences().getString(RecordUtil.CURRENT_VERSION, RecordUtil.VERSION_DEFAULT);

        // 是否新版本
        if (RecordUtil.isNewVersion(currentAppVersion, remoteAppVersion)) {
            RecordUtil.saveVersionNumber(remoteAppVersion);
        }
    }

    @Override
    protected void onResume() {
        // 注册eventbus
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 注销eventbus
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_WHATSNEW) {
            if (resultCode == RESULT_OK) {
                goToHome();
            } else {
                finish();
            }
        }
    }

}
