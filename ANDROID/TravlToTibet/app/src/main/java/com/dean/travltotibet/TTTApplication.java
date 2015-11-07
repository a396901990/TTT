package com.dean.travltotibet;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.dean.greendao.DaoMaster;
import com.dean.greendao.DaoSession;
import com.dean.travltotibet.database.DBHelper;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.PointManager;
import com.dean.travltotibet.util.ResourceUtil;

public class TTTApplication extends Application
{
    private static TTTApplication instance;
    
    private static ResourceUtil resourceUtil;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private static DBHelper dbHelper;

    private static SharedPreferences mSharedPreferences;

    private static Resources resources;

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        // 初始化颜色
        resourceUtil = new ResourceUtil(getApplicationContext());

        resources = getResources();

        // 初始化数据库
        initDB();

        // 初始化SharedPreferences
        initPreferences();

        // 初始化PointManager
        PointManager.init(instance);
    }

    private void initPreferences()
    {
        mSharedPreferences = getSharedPreferences(getResources().getString(R.string.preference), MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences()
    {
        return mSharedPreferences;
    }

    private void initDB() {
        dbHelper = DBHelper.getInstance(getApplicationContext());
        //dbHelper.initGeocodeData();
        //dbHelper.initDataBase();
        dbHelper.readDataBase(instance);
        //dbHelper.intoFileData();
    }

    public static TTTApplication getInstance()
    {
        return instance;
    }

    public static ResourceUtil getResourceUtil()
    {
        return resourceUtil;
    }

    public static DaoMaster getDaoMaster(Context context)
    {
        if (daoMaster == null)
        {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context)
    {
        if (daoSession == null)
        {
            if (daoMaster == null)
            {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static Resources getMyResources() {
        return resources;
    }

    public static Resources.Theme getMyTheme() {
        return context.getTheme();
    }
}
