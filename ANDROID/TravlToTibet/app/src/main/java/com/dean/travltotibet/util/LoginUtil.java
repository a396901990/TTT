package com.dean.travltotibet.util;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.UserInfo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.Platform;
import de.greenrobot.event.EventBus;

/**
 * Created by DeanGuo on 1/21/16.
 */
public final class LoginUtil {

    public final static String DEFAULT_PASSWORD = "123456";

    public static interface LoginListener {
        void onLoggedIn(LoginEvent event);

        void onLoggedOut(LogoutEvent event);

        void onLoggedInFailed(LoginFailedEvent event);
    }

    public static class LoginEvent {
        public boolean userChanged;

        public String token;

        public LoginEvent(boolean userChanged, String userToken) {
            this.userChanged = userChanged;
            this.token = userToken;
        }
    }

    private static LoginUtil sInstance = new LoginUtil();

    public static LoginUtil getInstance()
    {
        return sInstance;
    }

    public void uploadUserInfo(final UserInfo userInfo) {
        userInfo.setUsername(userInfo.getUserId());
        userInfo.setPassword(DEFAULT_PASSWORD);
        userInfo.signUp(TTTApplication.getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                bmobLogin(userInfo.getUserId());
            }

            @Override
            public void onFailure(int code, String msg) {
                if (msg.contains("already taken")) {
                    bmobLogin(userInfo.getUserId());
                } else {
                    loginFailed();
                }
            }
        });
    }

    public void bmobLogin(final String userId) {
        BmobUser.loginByAccount(TTTApplication.getContext(), userId, DEFAULT_PASSWORD, new LogInListener<UserInfo>() {

            @Override
            public void done(UserInfo user, BmobException e) {
                if (user != null) {
                    updateUserInfo();
                    EventBus.getDefault().post(new LoginUtil.LoginEvent(false, user.getUserId()));
                }
            }
        });
    }

    public void logout() {
        TTTApplication.setUserInfo(null);
        TTTApplication.setLogedIn(false);
        BmobUser.logOut(TTTApplication.getContext());
        EventBus.getDefault().post(new LoginUtil.LogoutEvent());
    }

    public void login( final Platform platform )
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(platform.getDb().getUserId());
        userInfo.setUserName(platform.getDb().getUserName());
        userInfo.setUserGender(platform.getDb().getUserGender());
        userInfo.setUserIcon(platform.getDb().getUserIcon());
        LoginUtil.getInstance().uploadUserInfo(userInfo);
    }

    public void updateUserInfo() {
        UserInfo userInfo = BmobUser.getCurrentUser(TTTApplication.getContext(), UserInfo.class);
        if (userInfo != null) {
            TTTApplication.setUserInfo(userInfo);
            TTTApplication.setLogedIn(true);
        } else {
            TTTApplication.setUserInfo(null);
            TTTApplication.setLogedIn(false);
        }
    }

    public void loginFailed() {
        TTTApplication.setLogedIn(false);
        TTTApplication.setUserInfo(null);
        EventBus.getDefault().post(new LoginUtil.LoginFailedEvent());
    }

    public static class LogoutEvent {

    }

    public static class LoginFailedEvent {

    }
}
