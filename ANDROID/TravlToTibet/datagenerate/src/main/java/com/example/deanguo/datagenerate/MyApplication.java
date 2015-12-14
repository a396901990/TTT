package com.example.deanguo.datagenerate;


import android.app.Application;
import android.content.Context;

import com.example.deanguo.greendao.DaoMaster;
import com.example.deanguo.greendao.DaoSession;
import com.example.deanguo.util.DBHelper;

public class MyApplication extends Application {
    private static MyApplication instance;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private static DBHelper dbHelper;

    private static Context context;

    public static final String DB_NAME = "TTT_DB";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        // 初始化数据库
        initDB();
    }

    private void initDB() {
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.initGeocodeData();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
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

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

}
