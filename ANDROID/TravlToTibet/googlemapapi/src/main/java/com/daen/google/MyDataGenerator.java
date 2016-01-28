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
//        geocodes.add(new Geocode("西海镇", 1, Constants.COUNTY));
        geocodes.add(new Geocode("江西沟乡", 2, Constants.VILLAGE));
        geocodes.add(new Geocode("黑马河乡", 3, Constants.VILLAGE));
        geocodes.add(new Geocode("石乃亥乡", 4, Constants.COUNTY));
        geocodes.add(new Geocode("泉吉乡", 5, Constants.CITY));
        geocodes.add(new Geocode("刚察县", 6, Constants.TOWN));
        geocodes.add(new Geocode("哈尔盖镇", 7, Constants.TOWN));
        geocodes.add(new Geocode("甘子河乡", 8, Constants.COUNTY));
        geocodes.add(new Geocode("西海镇", 9, Constants.VILLAGE));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
