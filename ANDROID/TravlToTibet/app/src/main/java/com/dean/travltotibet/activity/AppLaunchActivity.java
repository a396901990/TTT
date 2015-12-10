package com.dean.travltotibet.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dean.travltotibet.R;
import com.dean.travltotibet.ui.kbv.KenBurnsView;
import com.dean.travltotibet.util.AppUtil;
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

    private KenBurnsView mKenBurns;
    private ImageView mLogo;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.app_luanch_splash_screen);

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
                    gotoWhatsNew();
                } else {
                    gotoHome();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
        // 初始化视图
        //initView();
    }

    private void initView() {

        mKenBurns = (KenBurnsView) findViewById(R.id.ken_burns_images);
        mLogo = (ImageView) findViewById(R.id.logo);
        welcomeText = (TextView) findViewById(R.id.welcome_text);

        mKenBurns.setImageResource(R.drawable.splash_screen_background);

        logoAnimation();
        welcomeAnimation();
    }

    private void logoAnimation() {
        mLogo.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        mLogo.startAnimation(anim);
    }

    private void welcomeAnimation() {
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
        alphaAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!WelcomeActivity.hasShown(currentAppVersion)) {
                            gotoWhatsNew();
                        } else {
                            gotoHome();
                        }

                    }
                }, SPLASH_DISPLAY_LENGTH);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        alphaAnimation.setStartDelay(1700);
        alphaAnimation.setDuration(500);
        alphaAnimation.start();
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
    protected void onResume() {
        // 注册event bus
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 注销event bua
        super.onPause();
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
}
