package com.dean.travltotibet.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by DeanGuo on 3/19/16.
 */
public final class CountUtil {
    public final static String ARTICLE = "article";
    public final static String ROUTE = "route";
    public final static String TEAM_SEARCH = "team_search";
    public final static String PREPARE_INFO = "prepare_info";
    public final static String ROUTE_WAY_INFO = "route_way_info";

    public static void countArticle(Context mContext, String articleName) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("articleName", articleName);
        MobclickAgent.onEvent(mContext, ARTICLE, map);
    }

    public static void countRoute(Context mContext, String mRouteName, String mTravelType) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("routeName", mRouteName);
        map.put("travelType", mTravelType);
        MobclickAgent.onEvent(mContext, ROUTE, map);
    }

    public static void countTeamSearch(Context mContext, String mSearchWord) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("searchWord", mSearchWord);
        MobclickAgent.onEvent(mContext, TEAM_SEARCH, map);
    }

    public static void countPrepareInfo(Context mContext, String routeName, String prepareType, String travelType) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("routeName", routeName);
        map.put("prepareType", prepareType);
        map.put("travelType", travelType);
        MobclickAgent.onEvent(mContext, PREPARE_INFO, map);
    }

    public static void countRouteWayInfo(Context mContext, int position) {
        HashMap<String,String> map = new HashMap<String,String>();
        String routeWay = null;
        switch (position) {
            case 0:
                routeWay = "攻略";
                break;
            case 1:
                routeWay = "海拔";
                break;
            case 2:
                routeWay = "地图";
                break;
        }
        map.put("routeWay", routeWay);
        MobclickAgent.onEvent(mContext, ROUTE_WAY_INFO, map);
    }
}
