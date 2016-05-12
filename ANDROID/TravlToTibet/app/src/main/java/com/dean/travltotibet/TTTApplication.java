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
import com.dean.travltotibet.util.PicassoTools;
import com.dean.travltotibet.util.ResourceUtil;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;
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

    private static boolean logedIn;

    private static UserInfo userInfo;

    public static boolean hasLoggedIn() {
        return logedIn;
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
        AppUtil.saveVersionName(context);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        PicassoTools.clearCache();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        PicassoTools.clearCache();
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
        return new IconicsDrawable(context, icon).color(color).sizeDp(18);
    }
    public static void setUserInfo(UserInfo userInfo) {
        TTTApplication.userInfo = userInfo;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }
    public static boolean isLogedIn() {
        return logedIn;
    }

    public static void setLogedIn(boolean logedIn) {
        TTTApplication.logedIn = logedIn;
    }
}
