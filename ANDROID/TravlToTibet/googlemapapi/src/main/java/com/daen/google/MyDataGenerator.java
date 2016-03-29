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
//        geocodes.add(new Geocode("狮泉河镇", 1, Constants.CITY));
//        geocodes.add(new Geocode("左左乡", 2, Constants.VILLAGE));
//        geocodes.add(new Geocode("革吉县", 3, Constants.COUNTY));
//        geocodes.add(new Geocode("雄巴乡", 4, Constants.VILLAGE));
//        geocodes.add(new Geocode("盐湖乡", 5, Constants.VILLAGE));
//        geocodes.add(new Geocode("物玛乡", 6, Constants.VILLAGE));
//        geocodes.add(new Geocode("改则镇", 7, Constants.TOWN));
//
//        geocodes.add(new Geocode("洞措乡", 8, Constants.VILLAGE));
//        geocodes.add(new Geocode("中仓乡", 9, Constants.VILLAGE));
//        geocodes.add(new Geocode("尼玛县", 10, Constants.COUNTY));
//        geocodes.add(new Geocode("措折罗玛镇", 11, Constants.TOWN));
//        geocodes.add(new Geocode("布嘎村申扎县", 12, Constants.VILLAGE));
        geocodes.add(new Geocode("雄梅镇", 13, Constants.TOWN));

        geocodes.add(new Geocode("班戈县", 14, Constants.COUNTY));
        geocodes.add(new Geocode("下荣班戈县", 15, Constants.VILLAGE));
        geocodes.add(new Geocode("加苏村班戈县", 16, Constants.VILLAGE));
        geocodes.add(new Geocode("青龙乡班戈县", 17, Constants.VILLAGE));

        geocodes.add(new Geocode("东嘎村班戈县", 18, Constants.VILLAGE));
        geocodes.add(new Geocode("苦玛村班戈县", 19, Constants.VILLAGE));
        geocodes.add(new Geocode("纳木错乡", 20, Constants.VILLAGE));
        geocodes.add(new Geocode("当雄县", 21, Constants.COUNTY));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
