package com.dean.travltotibet;


import android.app.Application;

public class TTTApplication extends Application
{
    private static TTTApplication instance;
    
    private static ResourceUtil resourceUtil;

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
}
