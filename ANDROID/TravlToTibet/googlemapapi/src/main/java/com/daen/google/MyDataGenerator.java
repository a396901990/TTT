package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.util.DataGeneratorUtil;

import java.util.ArrayList;

import com.daen.google.module.Geocode;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();
//
//        geocodes.add(new Geocode("叶城县", 0, Constants.COUNTY));
//        geocodes.add(new Geocode("柯克亚乡", 1, Constants.VILLAGE));
//        geocodes.add(new Geocode("普萨村", 2, Constants.VILLAGE));
//        geocodes.add(new Geocode("叶城县阿克美其特村", 3, Constants.VILLAGE));
//        geocodes.add(new Geocode("库地", 4, Constants.TOWN));
//        geocodes.add(new Geocode("赛图拉", 5, Constants.VILLAGE));
//        geocodes.add(new Geocode("大红柳滩", 6, Constants.VILLAGE));
//        geocodes.add(new Geocode("日土县松西村", 7, Constants.VILLAGE));
//        geocodes.add(new Geocode("日土县多玛乡", 8, Constants.VILLAGE));
//        geocodes.add(new Geocode("日土县", 9, Constants.COUNTY));
//        geocodes.add(new Geocode("日松乡", 10, Constants.VILLAGE));
//        geocodes.add(new Geocode("狮泉河镇", 11, Constants.COUNTY));
//        geocodes.add(new Geocode("那木如村", 12, Constants.VILLAGE));
//        geocodes.add(new Geocode("索堆村", 13, Constants.VILLAGE));
//        geocodes.add(new Geocode("门士乡", 14, Constants.VILLAGE));
//        geocodes.add(new Geocode("普兰县巴嘎", 15, Constants.TOWN));
//        geocodes.add(new Geocode("霍尔乡", 16, Constants.VILLAGE));
//        geocodes.add(new Geocode("帕羊镇", 17, Constants.TOWN));
//        geocodes.add(new Geocode("仲巴县珠珠村", 18, Constants.VILLAGE));
//        geocodes.add(new Geocode("仲巴县", 19, Constants.COUNTY));
//        geocodes.add(new Geocode("拉藏乡", 20, Constants.VILLAGE));
//        geocodes.add(new Geocode("达吉岭乡", 21, Constants.VILLAGE));
//        geocodes.add(new Geocode("萨嘎县", 22, Constants.COUNTY));
//        geocodes.add(new Geocode("切热乡", 23, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉聂村", 24, Constants.VILLAGE));
//        geocodes.add(new Geocode("桑桑镇", 25, Constants.TOWN));
//        geocodes.add(new Geocode("昂仁县卡嘎镇", 26, Constants.TOWN));
//        geocodes.add(new Geocode("查务乡", 27, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉孜县", 28, Constants.COUNTY));
//        geocodes.add(new Geocode("热萨乡", 29, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉孜县柳乡", 30, Constants.VILLAGE));
//        geocodes.add(new Geocode("吉定镇", 31, Constants.TOWN));
        geocodes.add(new Geocode("日喀则", 32, Constants.CITY));
        geocodes.add(new Geocode("白朗县", 33, Constants.COUNTY));
        geocodes.add(new Geocode("江孜县", 34, Constants.COUNTY));
        geocodes.add(new Geocode("江孜县加热村", 35, Constants.VILLAGE));
        geocodes.add(new Geocode("热龙乡", 36, Constants.VILLAGE));
        geocodes.add(new Geocode("浪卡子县", 37, Constants.COUNTY));
        geocodes.add(new Geocode("达嘎乡", 38, Constants.VILLAGE));
        geocodes.add(new Geocode("曲水县", 39, Constants.COUNTY));
        geocodes.add(new Geocode("拉萨", 40, Constants.CITY));


        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
