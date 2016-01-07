package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorDIANZANG {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();


//        geocodes.add(new Geocode("昆明市", 0, Constants.CITY));
//        geocodes.add(new Geocode("安宁市", 1, Constants.CITY));
//        geocodes.add(new Geocode("禄脿镇", 2, Constants.TOWN));
//        geocodes.add(new Geocode("禄丰县", 3, Constants.COUNTY));
//        geocodes.add(new Geocode("一平浪镇", 4, Constants.TOWN));
//        geocodes.add(new Geocode("苍岭镇", 5, Constants.TOWN));
//        geocodes.add(new Geocode("楚雄", 6, Constants.CITY));
//        geocodes.add(new Geocode("紫溪镇", 7, Constants.TOWN));
//        geocodes.add(new Geocode("南华县", 8, Constants.COUNTY));
//        geocodes.add(new Geocode("沙桥镇", 9, Constants.TOWN));
//        geocodes.add(new Geocode("天申堂乡", 10, Constants.VILLAGE));
//        geocodes.add(new Geocode("祥云县下庄镇", 11, Constants.TOWN));
//        geocodes.add(new Geocode("祥云县", 12, Constants.COUNTY));
//        geocodes.add(new Geocode("海坝庄村", 13, Constants.VILLAGE));
//
//        geocodes.add(new Geocode("大理", 14, Constants.CITY));
//        geocodes.add(new Geocode("大理玉洱路", 15, Constants.CITY));
//        geocodes.add(new Geocode("喜洲镇", 16, Constants.TOWN));
//        geocodes.add(new Geocode("焦石洞", 17, Constants.VILLAGE));
//        geocodes.add(new Geocode("西邑镇", 18, Constants.TOWN));
//        geocodes.add(new Geocode("松桂镇", 19, Constants.TOWN));
//        geocodes.add(new Geocode("鹤庆县", 20, Constants.COUNTY));
//
//        geocodes.add(new Geocode("丽江市", 21, Constants.CITY));
//        geocodes.add(new Geocode("虎跳峡镇", 22, Constants.TOWN));
//        geocodes.add(new Geocode("俄迪隧道", 23, Constants.TUNNEL));
//        geocodes.add(new Geocode("香格里拉市宝山村", 24, Constants.TUNNEL));
//        geocodes.add(new Geocode("上吉沙", 25, Constants.TUNNEL));
//        geocodes.add(new Geocode("小中甸镇", 26, Constants.TOWN));
//        geocodes.add(new Geocode("香格里拉市", 27, Constants.COUNTY));
//        geocodes.add(new Geocode("香格里拉市哈拉", 28, Constants.COUNTY));
//        geocodes.add(new Geocode("奔子栏镇", 29, Constants.TOWN));
//        geocodes.add(new Geocode("书松村", 30, Constants.TOWN));
//        geocodes.add(new Geocode("云南省迪庆藏族自治州德钦县", 31, Constants.COUNTY));
//        geocodes.add(new Geocode("德钦县佛山乡", 32, Constants.COUNTY));
//        geocodes.add(new Geocode("纳西民族乡", 33, Constants.VILLAGE));
//        geocodes.add(new Geocode("芒康戈底", 34, Constants.VILLAGE));


////
//        geocodes.add(new Geocode("芒康县", 35, Constants.COUNTY));
//        geocodes.add(new Geocode("如美镇", 36, Constants.TOWN));
//        geocodes.add(new Geocode("登巴村", 37,"芒康县", Constants.VILLAGE));
//        geocodes.add(new Geocode("左贡县", 38, Constants.COUNTY));
//        geocodes.add(new Geocode("田妥镇", 39, Constants.TOWN));
//        geocodes.add(new Geocode("斜库村", 40, Constants.VILLAGE));
//        geocodes.add(new Geocode("邦达镇", 41, Constants.TOWN));
//        geocodes.add(new Geocode("同尼村", 42, Constants.VILLAGE));
//        geocodes.add(new Geocode("八宿县", 43, Constants.COUNTY));
//        geocodes.add(new Geocode("吉达乡", 44, Constants.VILLAGE));
//        geocodes.add(new Geocode("然乌镇", 45, Constants.TOWN));
//        geocodes.add(new Geocode("宗坝村", 46, Constants.VILLAGE));
//        geocodes.add(new Geocode("松宗镇", 47, Constants.TOWN));
//        geocodes.add(new Geocode("波密县", 48, Constants.COUNTY));
//        geocodes.add(new Geocode("古乡", 49, Constants.VILLAGE));
//        geocodes.add(new Geocode("通麦大桥", 50, Constants.BRIDGE));
//        geocodes.add(new Geocode("林芝排龙村", 51,Constants.VILLAGE));
//        geocodes.add(new Geocode("林芝排龙村", 52, Constants.VILLAGE));
//        geocodes.add(new Geocode("鲁朗镇", 53, Constants.TOWN));
//        geocodes.add(new Geocode("八一镇", 54, Constants.TOWN));
//        geocodes.add(new Geocode("更张村", 55, Constants.VILLAGE));
//        geocodes.add(new Geocode("百巴镇", 56, Constants.TOWN));
//        geocodes.add(new Geocode("工布江达镇", 57, Constants.TOWN));
//        geocodes.add(new Geocode("金达镇", 58, Constants.TOWN));
//        geocodes.add(new Geocode("加兴乡", 59, Constants.VILLAGE));
//        geocodes.add(new Geocode("日多乡", 60, Constants.VILLAGE));
//        geocodes.add(new Geocode("墨竹工卡县", 61, Constants.COUNTY));
//        geocodes.add(new Geocode("达孜县", 62, Constants.COUNTY));
//        geocodes.add(new Geocode("拉萨", 63, Constants.CITY));
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
