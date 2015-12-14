package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();

//        geocodes.add(new Geocode("昆明", 0,"云南省", Constants.CITY));
//        geocodes.add(new Geocode("安宁市", 1, Constants.CITY));
//        geocodes.add(new Geocode("禄丰县", 2, Constants.COUNTY));
//        geocodes.add(new Geocode("楚雄", 3, Constants.CITY));
//        geocodes.add(new Geocode("南华县", 4, Constants.COUNTY));
//        geocodes.add(new Geocode("大理", 6, Constants.CITY));
//        geocodes.add(new Geocode("喜洲镇", 7, Constants.TOWN));
//        geocodes.add(new Geocode("西邑镇", 8, Constants.TOWN));
//        geocodes.add(new Geocode("松桂镇", 9, Constants.TOWN));
//        geocodes.add(new Geocode("鹤庆县", 10, Constants.COUNTY));
//        geocodes.add(new Geocode("丽江市", 11, Constants.CITY));

        geocodes.add(new Geocode("丽江市", 11, Constants.CITY));
        geocodes.add(new Geocode("拉市海", 12, Constants.CITY));
        geocodes.add(new Geocode("虎跳峡镇", 13, Constants.COUNTY));
        geocodes.add(new Geocode("俄迪隧道", 14, Constants.TUNNEL));
        geocodes.add(new Geocode("小中甸镇", 15, Constants.TOWN));
        geocodes.add(new Geocode("香格里拉县", 16, Constants.COUNTY));
        geocodes.add(new Geocode("奔子栏镇", 17, Constants.TOWN));
        geocodes.add(new Geocode("书松村", 18, Constants.TOWN));
        geocodes.add(new Geocode("德钦隧道", 19, Constants.TUNNEL));
        geocodes.add(new Geocode("纳西民族乡", 20, Constants.VILLAGE));
        geocodes.add(new Geocode("毛尼村", 21, Constants.VILLAGE));
        geocodes.add(new Geocode("芒康县", 22, Constants.COUNTY));

//        geocodes.add(new Geocode("理塘县", 23, Constants.COUNTY));
//        geocodes.add(new Geocode("巴塘县", 24, Constants.COUNTY));
//        geocodes.add(new Geocode("竹巴龙乡", 25, Constants.COUNTY));
//        geocodes.add(new Geocode("海通兵站", 26, Constants.OTHERS));
//        geocodes.add(new Geocode("芒康县", 27, Constants.COUNTY));
////
//        geocodes.add(new Geocode("芒康县", 27, Constants.COUNTY));
//        geocodes.add(new Geocode("如美镇", 28, Constants.TOWN));
//        geocodes.add(new Geocode("登巴村", 29,"芒康县", Constants.VILLAGE));
//        geocodes.add(new Geocode("左贡县", 30, Constants.COUNTY));
//        geocodes.add(new Geocode("田妥镇", 31, Constants.TOWN));
//        geocodes.add(new Geocode("斜库村", 32, Constants.VILLAGE));
//        geocodes.add(new Geocode("邦达镇", 33, Constants.TOWN));
//        geocodes.add(new Geocode("同尼村", 34, Constants.VILLAGE));
//        geocodes.add(new Geocode("八宿县", 35, Constants.COUNTY));

//        geocodes.add(new Geocode("八宿县", 35, Constants.COUNTY));
//        geocodes.add(new Geocode("吉达乡", 36, Constants.VILLAGE));
//        geocodes.add(new Geocode("然乌镇", 37, Constants.TOWN));
//        geocodes.add(new Geocode("宗坝村", 38, Constants.VILLAGE));
//        geocodes.add(new Geocode("松宗镇", 39, Constants.TOWN));
//        geocodes.add(new Geocode("波密县", 40, Constants.COUNTY));
//        geocodes.add(new Geocode("古乡", 41, Constants.VILLAGE));
//        geocodes.add(new Geocode("通麦大桥", 42, Constants.BRIDGE));
//        geocodes.add(new Geocode("排龙村", 43, "林芝",Constants.VILLAGE));
//        geocodes.add(new Geocode("东久村", 44, "林芝",Constants.VILLAGE));
//        geocodes.add(new Geocode("鲁朗镇", 45, Constants.TOWN));

//
//        geocodes.add(new Geocode("鲁朗镇", 45, Constants.TOWN));
//        geocodes.add(new Geocode("八一镇", 47, Constants.TOWN));
//        geocodes.add(new Geocode("更张村", 48, Constants.VILLAGE));
//        geocodes.add(new Geocode("百巴镇", 49, Constants.TOWN));
//        geocodes.add(new Geocode("工布江达镇", 50, Constants.TOWN));
//        geocodes.add(new Geocode("金达镇", 51, Constants.TOWN));
//        geocodes.add(new Geocode("加兴乡", 52, Constants.VILLAGE));
//        geocodes.add(new Geocode("日多乡", 54, Constants.VILLAGE));
//        geocodes.add(new Geocode("墨竹工卡县", 55, Constants.COUNTY));
//        geocodes.add(new Geocode("达孜县", 56, Constants.COUNTY));
//        geocodes.add(new Geocode("拉萨", 57, Constants.CITY));

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
