package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorXINZANG {
    private static ArrayList<com.daen.google.module.Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<com.daen.google.module.Geocode>();


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
        geocodes.add(new com.daen.google.module.Geocode("拉孜县柳乡", 30, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("吉定镇", 31, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("日喀则", 32, com.daen.google.module.Constants.CITY));
        geocodes.add(new com.daen.google.module.Geocode("白朗县", 33, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("江孜县", 34, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("江孜县加热村", 35, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("热龙乡", 36, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("浪卡子县", 37, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("达嘎乡", 38, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("曲水县", 39, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("拉萨", 40, com.daen.google.module.Constants.CITY));


//珠峰
//        geocodes.add(new Geocode("拉萨", 0, Constants.CITY));
//        geocodes.add(new Geocode("曲水县", 1, Constants.COUNTY));
//        geocodes.add(new Geocode("达嘎乡", 2, Constants.VILLAGE));
//        geocodes.add(new Geocode("浪卡子县", 3, Constants.COUNTY));
//        geocodes.add(new Geocode("热龙乡", 4, Constants.VILLAGE));
//        geocodes.add(new Geocode("江孜县加热村", 5, Constants.VILLAGE));
//        geocodes.add(new Geocode("白朗县", 6, Constants.COUNTY));
//        geocodes.add(new Geocode("日喀则", 7, Constants.CITY));
//        geocodes.add(new Geocode("吉定镇", 8, Constants.TOWN));
//        geocodes.add(new Geocode("热萨乡", 9, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉孜县", 10, Constants.COUNTY));
//
//        geocodes.add(new Geocode("定日县白坝村", 11, Constants.VILLAGE));
//        geocodes.add(new Geocode("扎西宗乡", 1, Constants.COUNTY));
//        geocodes.add(new Geocode("扎西通门", 2, Constants.VILLAGE));
        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(com.daen.google.module.Geocode geocode) {

            }

            @Override
            public void fetchFinish(ArrayList<com.daen.google.module.Geocode> geos) {
                Collections.sort(geos, com.daen.google.module.Geocode.MileageComparator);
                //ParseJson.parseToFile(geos);

                PathRunnable pathRunnable = new PathRunnable(geos, new PathRunnable.FetchCallback() {

                    @Override
                    public void fetchSuccess(com.daen.google.module.Geocode geocode) {
                    }

                    @Override
                    public void fetchFinished(ArrayList<com.daen.google.module.Geocode> geocodes) {
                        Collections.sort(geocodes, com.daen.google.module.Geocode.MileageComparator);
                        com.daen.google.util.ParseJson.parseToFile(geocodes);
                    }

                    @Override
                    public void fetchFail() {

                    }
                });

                Thread t = new Thread(pathRunnable);
                t.start();
            }

            @Override
            public void fetchFail() {

            }
        }
        );

        Thread t = new Thread(detailsInfoRunnable);
        t.start();
    }
}
