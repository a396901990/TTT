package com.dean.travltotibet.util;

import com.dean.travltotibet.TTTApplication;

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

    public void login( final String userID )
    {
        if (userID == null)
        {
            mToken = null;
            mUserChanged = true;
        }
        else
        {
            mToken = userID;
            mUserChanged = !mToken.equals(getLastToken());

            if (mUserChanged)
            {
                saveToken();
            }
        }
        TTTApplication.setLoggedIn(mUserChanged, mToken);
    }

    public static class LogoutEvent {

    }

    public static class LoginFailedEvent {

    }

    public String getLastToken()
    {
        return TTTApplication.getSharedPreferences().getString(Constants.KEY_LAST_TOKEN, null);
    }

    private void saveToken()
    {
        TTTApplication.getSharedPreferences().edit().putString(Constants.KEY_LAST_TOKEN, mToken).commit();
    }
}
