package com.dean.travltotibet.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dean.greendao.RoutePlan;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.activity.InfoActivity;
import com.dean.travltotibet.model.TravelType;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.AnimUtil;
import com.dean.travltotibet.util.IntentExtra;
import com.dean.travltotibet.util.LoginUtil;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by DeanGuo on 1/21/16.
 */
public class LoginDialog extends DialogFragment implements PlatformActionListener{

    private View contentLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PopupDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentLayout = LayoutInflater.from(getActivity()).inflate(R.layout.login_page, null);
        ImageView qq = (ImageView) contentLayout.findViewById(R.id.login_qq);
        ImageView wechat = (ImageView) contentLayout.findViewById(R.id.login_wechat);
        ImageView sina = (ImageView) contentLayout.findViewById(R.id.login_sina);

        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), QQ.NAME);
            }
        });

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), Wechat.NAME);
            }
        });

        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(v, v.getTag(), SinaWeibo.NAME);
            }
        });


        return contentLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }

    protected void notifyItemClicked(final View item, final Object obj, final String type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = AnimUtil.shake(item, 3f);
                animator.setRepeatCount(0);

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        login(type);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                animator.start();

            }
        }, 0);
    }

    private void login(String loginType) {

        Platform platform = ShareSDK.getPlatform(getActivity(), loginType);
        platform.setPlatformActionListener(this);

        if (platform.isAuthValid()) {
            String userId = platform.getDb().getUserId();
            if (userId != null) {
                Log.e("userId:", userId);
            }
        }
        platform.SSOSetting(false);
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Log.e("onComplete:", "onComplete");
        getDialog().dismiss();
        // 成功则登陆
        LoginUtil.getInstance().login(platform.getDb().getToken());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.e("onError:", "onError");
        getDialog().dismiss();
        // 不成功执行失败操作
        TTTApplication.loginFailed();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.e("onCancel:", "onCancel");
        getDialog().dismiss();
    }
}