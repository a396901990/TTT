package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorXINZANG {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("叶城县", 0, Constants.COUNTY));
        geocodes.add(new Geocode("柯克亚乡", 1, Constants.VILLAGE));
        geocodes.add(new Geocode("普萨村", 2, Constants.VILLAGE));
        geocodes.add(new Geocode("叶城县阿克美其特村", 3, Constants.VILLAGE));
        geocodes.add(new Geocode("库地", 4, Constants.TOWN));
        geocodes.add(new Geocode("赛图拉", 5, Constants.VILLAGE));
        geocodes.add(new Geocode("大红柳滩", 6, Constants.VILLAGE));
        geocodes.add(new Geocode("日土县松西村", 7, Constants.VILLAGE));
        geocodes.add(new Geocode("日土县多玛乡", 8, Constants.VILLAGE));
        geocodes.add(new Geocode("日土县", 9, Constants.COUNTY));
        geocodes.add(new Geocode("日松乡", 10, Constants.VILLAGE));
        geocodes.add(new Geocode("狮泉河镇", 11, Constants.COUNTY));
        geocodes.add(new Geocode("那木如村", 12, Constants.VILLAGE));
        geocodes.add(new Geocode("索堆村", 13, Constants.VILLAGE));
        geocodes.add(new Geocode("门士乡", 14, Constants.VILLAGE));
        geocodes.add(new Geocode("巴嘎", 15, "普兰县", Constants.TOWN));
        geocodes.add(new Geocode("霍尔乡", 16, Constants.VILLAGE));
        geocodes.add(new Geocode("帕羊镇", 17, Constants.TOWN));
        geocodes.add(new Geocode("仲巴县珠珠村", 17, Constants.VILLAGE));
        geocodes.add(new Geocode("仲巴县", 18, Constants.COUNTY));
        geocodes.add(new Geocode("拉藏乡", 19, Constants.VILLAGE));
        geocodes.add(new Geocode("达吉岭乡", 20, Constants.VILLAGE));
        geocodes.add(new Geocode("萨嘎县", 21, Constants.COUNTY));
        geocodes.add(new Geocode("切热乡", 22, Constants.VILLAGE));
        geocodes.add(new Geocode("拉聂村", 23, Constants.VILLAGE));
        geocodes.add(new Geocode("桑桑镇", 24, Constants.TOWN));
        geocodes.add(new Geocode("卡嘎镇", 25, Constants.TOWN));
        geocodes.add(new Geocode("查务乡", 26, Constants.VILLAGE));
        geocodes.add(new Geocode("拉孜县", 27, Constants.COUNTY));
        geocodes.add(new Geocode("热萨乡", 28, Constants.VILLAGE));
        geocodes.add(new Geocode("拉孜县柳乡", 28, Constants.VILLAGE));
        geocodes.add(new Geocode("吉定镇", 30, Constants.TOWN));
        geocodes.add(new Geocode("日喀则", 31, Constants.CITY));
        geocodes.add(new Geocode("白朗县", 32, Constants.COUNTY));
        geocodes.add(new Geocode("江孜县", 33, Constants.COUNTY));
        geocodes.add(new Geocode("江孜县加热村", 34, Constants.VILLAGE));
        geocodes.add(new Geocode("热龙乡", 35, Constants.VILLAGE));
        geocodes.add(new Geocode("浪卡子县", 36, Constants.COUNTY));
        geocodes.add(new Geocode("达嘎乡", 37, Constants.VILLAGE));
        geocodes.add(new Geocode("曲水县", 38, Constants.COUNTY));
        geocodes.add(new Geocode("拉萨", 39, Constants.CITY));

        DetailsInfoRunnable detailsInfoRunnable = new DetailsInfoRunnable(geocodes, new DetailsInfoRunnable.FetchCallback() {
            @Override
            public void fetchSuccess(Geocode geocode) {

            }

            @Override
            public void fetchFinish(ArrayList<Geocode> geos) {
                Collections.sort(geos, Geocode.MileageComparator);
                //ParseJson.parseToFile(geos);

                PathRunnable pathRunnable = new PathRunnable(geos, new PathRunnable.FetchCallback() {

                    @Override
                    public void fetchSuccess(Geocode geocode) {
                    }

                    @Override
                    public void fetchFinished(ArrayList<Geocode> geocodes) {
                        Collections.sort(geocodes, Geocode.MileageComparator);
                        ParseJson.parseToFile(geocodes);
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
