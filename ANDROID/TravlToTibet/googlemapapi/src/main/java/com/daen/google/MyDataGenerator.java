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
        geocodes.add(new Geocode("丙中洛乡", 1, Constants.VILLAGE));
        geocodes.add(new Geocode("云南省石门关", 2, Constants.VILLAGE));
        geocodes.add(new Geocode("秋那桶村", 3, Constants.VILLAGE));
        geocodes.add(new Geocode("腊它底", 4, Constants.VILLAGE));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
