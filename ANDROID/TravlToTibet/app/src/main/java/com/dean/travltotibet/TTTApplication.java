package com.dean.travltotibet;


import android.app.Application;
import android.content.Context;

import com.dean.greendao.DaoMaster;
import com.dean.greendao.DaoSession;
import com.dean.travltotibet.util.DataBaseInfo;
import com.dean.travltotibet.util.ResourceUtil;

public class TTTApplication extends Application
{
    private static TTTApplication instance;
    
    private static ResourceUtil resourceUtil;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        
        resourceUtil = new ResourceUtil(getApplicationContext());
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
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DataBaseInfo.DB_NAME, null);
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

}
