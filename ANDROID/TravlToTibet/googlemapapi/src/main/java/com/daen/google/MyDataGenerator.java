package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.util.DataGeneratorUtil;

import java.util.ArrayList;

import com.daen.google.module.Geocode;

/**
 * Created by 95 on 2015/5/29.
 * https://maps.googleapis.com/maps/api/geocode/json?address=江西沟乡&sensor=false&key=AIzaSyAoEvnY8lEliDQ5JWprEQ16OBFZ7SheZ-Q&language=zh-CN
 */
public class MyDataGenerator {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();
//        geocodes.add(new Geocode("倒淌河镇", 1, Constants.TOWN));
//        geocodes.add(new Geocode("共和县", 2, Constants.COUNTY));
//        geocodes.add(new Geocode("铁盖乡", 3, Constants.VILLAGE));
//        geocodes.add(new Geocode("河卡镇", 4, Constants.TOWN));
//        geocodes.add(new Geocode("青根河村", 5, Constants.VILLAGE));
//        geocodes.add(new Geocode("花石峡镇", 6, Constants.TOWN));
//        geocodes.add(new Geocode("玛多县", 7, Constants.COUNTY));
        geocodes.add(new Geocode("青海省清水河镇", 8, Constants.TOWN));
        geocodes.add(new Geocode("珍秦镇", 9, Constants.TOWN));
        geocodes.add(new Geocode("歇武镇", 10, Constants.TOWN));
        geocodes.add(new Geocode("玉树", 11, Constants.CITY));
        geocodes.add(new Geocode("下拉秀镇", 12, Constants.TOWN));
        geocodes.add(new Geocode("囊谦县", 13, Constants.COUNTY));
        geocodes.add(new Geocode("类乌齐镇", 14, Constants.TOWN));
        geocodes.add(new Geocode("类乌齐县", 15, Constants.COUNTY));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
