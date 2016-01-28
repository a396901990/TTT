package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.module.Geocode;
import com.daen.google.util.DataGeneratorUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorZhufeng {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();

        geocodes.add(new Geocode("拉孜县", 0, Constants.COUNTY));
        geocodes.add(new Geocode("查务乡", 1, Constants.VILLAGE));
        geocodes.add(new Geocode("定日县白坝村", 2, Constants.VILLAGE));
        geocodes.add(new Geocode("扎西宗乡", 3, Constants.VILLAGE));
        geocodes.add(new Geocode("扎西通门", 4, Constants.SCENIC_SPOT));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
