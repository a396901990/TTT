package com.dean.travltotibet;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.dean.greendao.DaoMaster;
import com.dean.greendao.DaoSession;
import com.dean.travltotibet.database.DBHelper;
import com.dean.travltotibet.model.UserInfo;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.ui.chart.PointManager;
import com.dean.travltotibet.util.LoginUtil;
import com.dean.travltotibet.util.ResourceUtil;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import de.greenrobot.event.EventBus;

public class TTTApplication extends Application {

    private static TTTApplication instance;

    private static ResourceUtil resourceUtil;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private static DBHelper dbHelper;

    private static SharedPreferences mSharedPreferences;

    private static Resources resources;

    private static Context context;

    private static boolean logedin;

    private static UserInfo userInfo;

    public static boolean hasLoggedIn() {
        return logedin;
    }

    public static void setLoggedIn(boolean isUserChanged, String userToken) {
        updateUserInfo(userToken);
        EventBus.getDefault().post(new LoginUtil.LoginEvent(isUserChanged, userToken));
    }

    public static void logout() {
        logedin = false;
        updateUserInfo("");
        EventBus.getDefault().post(new LoginUtil.LogoutEvent());
    }

    public static void loginFailed() {
        logedin = false;
        EventBus.getDefault().post(new LoginUtil.LoginFailedEvent());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        // 初始化颜色
        resourceUtil = new ResourceUtil(getApplicationContext());

        resources = getResources();

        // 初始化SharedPreferences
        initPreferences();

        // 初始化数据库
        initDB();

        // 初始化PointManager
        PointManager.init(instance);
        AppUtil.saveVersionCode(context);

        // 初始化share sdk
        ShareSDK.initSDK(context);

        // 设置登陆状态
        initLoginStatus();
    }

    /**
     * 初始化用户信息和登陆状态
     */
    private static void initLoginStatus() {
        String lastToken = LoginUtil.getInstance().getLastToken();
        updateUserInfo(lastToken);
    }

    private static void updateUserInfo(String token) {

        Platform[] platforms = ShareSDK.getPlatformList();
        if (TextUtils.isEmpty(token)) {
            userInfo = null;
            logedin = false;
        } else {
            for (Platform platform : platforms) {
                // token相同，可以登陆
                if (token.equals(platform.getDb().getToken())) {
                    userInfo = new UserInfo();
                    userInfo.setUserIcon(platform.getDb().getUserIcon());
                    userInfo.setUserName(platform.getDb().getUserName());
                    userInfo.setUserGender(platform.getDb().getUserGender());
                    logedin = true;
                }
            }
        }
    }

    private void initPreferences() {
        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.preference), MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    private void initDB() {
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.readDataBase(instance);
//        dbHelper.initGeocodeData();
    }

    public static TTTApplication getInstance() {
        return instance;
    }

    public static ResourceUtil getResourceUtil() {
        return resourceUtil;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static Context getContext() {
        return context;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static Resources getMyResources() {
        return resources;
    }

    public static int getMyColor(int resoureceId) {
        return resources.getColor(resoureceId);
    }

    public static Resources.Theme getMyTheme() {
        return context.getTheme();
    }

    public static Drawable getGoogleIconDrawable(final IIcon icon, int color) {
        return new IconicsDrawable(context, icon).color(color).sizeDp(20);
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }
}
