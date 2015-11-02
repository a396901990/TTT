package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {

    public static void main(String[] args) throws Exception {

        final ArrayList<Geocode> geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("叶城县", 0, Constants.COUNTY));
        geocodes.add(new Geocode("柯克亚乡", 62, Constants.VILLAGE));
//        geocodes.add(new Geocode("普萨村", 72, Constants.VILLAGE));
//        geocodes.add(new Geocode("阿克美其特村", 100, "叶城县", Constants.VILLAGE));
//        geocodes.add(new Geocode("库地", 160, Constants.TOWN));
//        geocodes.add(new Geocode("赛图拉", 355, Constants.VILLAGE));
//        geocodes.add(new Geocode("大红柳滩", 487, Constants.VILLAGE));
//        //geocodes.add(new Geocode("日土县松西村", 732, Constants.VILLAGE));
//        geocodes.add(new Geocode("多玛乡", 829, "日土县", Constants.TOWN));
//        geocodes.add(new Geocode("日土县", 932, Constants.COUNTY));
//        geocodes.add(new Geocode("日松乡", 965, Constants.VILLAGE));
//        geocodes.add(new Geocode("狮泉河镇", 1058, Constants.COUNTY));
//        geocodes.add(new Geocode("索堆村", 1202, Constants.VILLAGE));
//        geocodes.add(new Geocode("巴嘎", 1321, "普兰县", Constants.TOWN));
//        geocodes.add(new Geocode("霍尔乡", 1364, Constants.VILLAGE));
//        geocodes.add(new Geocode("帕羊镇", 1585, Constants.TOWN));
//        geocodes.add(new Geocode("仲巴县", 1652, Constants.COUNTY));
//        geocodes.add(new Geocode("拉藏乡", 1760, Constants.VILLAGE));
//        geocodes.add(new Geocode("达吉岭乡", 1819, Constants.VILLAGE));
//        geocodes.add(new Geocode("萨嘎县", 1846, Constants.COUNTY));
//        geocodes.add(new Geocode("切热乡", 1940, Constants.VILLAGE));
//        geocodes.add(new Geocode("桑桑镇", 2025, Constants.TOWN));
//        geocodes.add(new Geocode("卡嘎镇", 2087, Constants.TOWN));
//        geocodes.add(new Geocode("查务乡", 2139, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉孜县", 2146, Constants.COUNTY));
//        geocodes.add(new Geocode("热萨乡", 2189, Constants.VILLAGE));
//        geocodes.add(new Geocode("柳乡", 2213, Constants.VILLAGE));
//        geocodes.add(new Geocode("吉定镇", 2236, Constants.TOWN));
//        geocodes.add(new Geocode("日喀则", 2291, Constants.CITY));
//        geocodes.add(new Geocode("白朗县", 2341, Constants.COUNTY));
//        geocodes.add(new Geocode("江孜县", 2382, Constants.COUNTY));
//        geocodes.add(new Geocode("加热村", 2407,"江孜县", Constants.VILLAGE));
//        geocodes.add(new Geocode("热龙乡", 2437, Constants.VILLAGE));
//        geocodes.add(new Geocode("浪卡子县", 2485, Constants.COUNTY));
//        geocodes.add(new Geocode("达嘎乡", 2577, Constants.VILLAGE));
//        geocodes.add(new Geocode("拉萨", 2651, Constants.CITY));

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
                        //ParseJson.parseToFile(geocodes);
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
