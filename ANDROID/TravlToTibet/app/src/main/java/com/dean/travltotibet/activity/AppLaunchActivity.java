package com.dean.travltotibet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.LoginUtil;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.sharesdk.framework.ShareSDK;


/**
 * Created by DeanGuo on 11/7/15.
 */
public class AppLaunchActivity extends Activity {

    public final static String BMOB_APPLICATION_ID = "1ac4c82c189eb0d80711885ed3ad05ba";

    private final int SPLASH_DISPLAY_LENGTH = 0;

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

        setContentView(R.layout.app_luanch_splash_screen);

        // 初始化Bmob
        Bmob.initialize(this, BMOB_APPLICATION_ID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, BMOB_APPLICATION_ID);

        // 初始化share sdk
        ShareSDK.initSDK(this);

        // 设置登陆状态
        LoginUtil.getInstance().updateUserInfo();

        // 6.0 检查运行时权限
        checkSystemPermission();
    }

    private void checkSystemPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        } else {
            initView();
        }
    }

    private void initView() {

         logoTextAnimation();
    }


    private void logoTextAnimation() {
        ImageView logoText = (ImageView) this.findViewById(R.id.logo_text);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logoMsgAnimation();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logoPicAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnimation.setDuration(1000);
        logoText.startAnimation(alphaAnimation);
    }

    private void logoMsgAnimation() {
        View logoMsg = this.findViewById(R.id.logo_msg);
        logoMsg.setVisibility(View.VISIBLE);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        logoMsg.startAnimation(alphaAnimation);
    }

    private void logoPicAnimation() {
        final ImageView logoPic = (ImageView) this.findViewById(R.id.logo_pic);
        logoPic.setVisibility(View.VISIBLE);
        final Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.logo_slide_down);
        slideDown.setInterpolator(new BounceInterpolator());
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logoPic.startAnimation(slideDown);
    }

    public void goTo() {
        // 获取当前app版本
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String currentAppVersion = AppUtil.getVersionName(getApplicationContext());
                if (!WelcomeActivity.hasShown(currentAppVersion)) {
                    gotoWhatsNew();
                } else {
                    gotoHome();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    /**
     * 进入home界面
     */
    public void gotoHome() {
        Intent intent = new Intent(getApplication(), HomeActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    /**
     * 进入WhatsNew界面
     */
    public void gotoWhatsNew() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivityForResult(intent, REQUEST_WELCOME);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_WELCOME) {
            if (resultCode == RESULT_OK) {
                gotoHome();
                finish();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initView();
    }
}
