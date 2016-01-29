package com.dean.travltotibet.util;

import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.UserInfo;

import cn.sharesdk.framework.Platform;

/**
 * Created by DeanGuo on 1/21/16.
 */
public final class LoginUtil {

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
    private String mToken;

    private boolean mUserChanged;

    private static LoginUtil sInstance = new LoginUtil();

    public static LoginUtil getInstance()
    {
        return sInstance;
    }

    private LoginUtil()
    {
    }

    public void login( final String token )
    {
        if (token == null)
        {
            mToken = null;
            mUserChanged = true;
        }
        else
        {
            mToken = token;
            mUserChanged = !mToken.equals(getLastToken());

            if (mUserChanged)
            {
                saveToken();
            }
        }
        TTTApplication.setLoggedIn(mUserChanged, mToken);
    }

    public void logout() {
        mToken = getLastToken();
        if (mToken != null) {
            mToken = "";
            saveToken();
        }
        TTTApplication.logout();
    }


    public void login( final Platform platform )
    {
        String token = platform.getDb().getToken();
        if (token == null)
        {
            mToken = null;
            mUserChanged = true;
        }
        else
        {
            mToken = token;
            mUserChanged = !mToken.equals(getLastToken());

            if (mUserChanged)
            {
                saveToken();
            }
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(platform.getDb().getUserName());
        userInfo.setUserGender(platform.getDb().getUserGender());
        userInfo.setUserIcon(platform.getDb().getUserIcon());

        TTTApplication.setLoggedIn(mUserChanged, mToken);
    }

    public static class LogoutEvent {

    }

    public static class LoginFailedEvent {

    }

    public String getLastToken()
    {
        return TTTApplication.getSharedPreferences().getString(Constants.KEY_LAST_TOKEN, "");
    }

    private void saveToken()
    {
        TTTApplication.getSharedPreferences().edit().putString(Constants.KEY_LAST_TOKEN, mToken).commit();
    }
}
