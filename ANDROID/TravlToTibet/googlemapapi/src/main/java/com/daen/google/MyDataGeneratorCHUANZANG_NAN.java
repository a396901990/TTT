package com.daen.google;

import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorCHUANZANG_NAN {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();

//        geocodes.add(new Geocode("成都", 0, Constants.CITY));
//        geocodes.add(new Geocode("新津县", 1, Constants.COUNTY));
//        geocodes.add(new Geocode("邛崃市", 2, Constants.CITY));
//        geocodes.add(new Geocode("名山区", 3, Constants.COUNTY));
//        geocodes.add(new Geocode("金鸡关隧道", 4, Constants.TUNNEL));
//        geocodes.add(new Geocode("雅安", 5, Constants.CITY));
//        geocodes.add(new Geocode("多营镇", 6, Constants.TOWN));
//        geocodes.add(new Geocode("飞仙关镇", 7, Constants.TOWN));
//        geocodes.add(new Geocode("天全县", 8, Constants.COUNTY));
//        geocodes.add(new Geocode("紫石乡", 9, Constants.VILLAGE));
//        geocodes.add(new Geocode("二郎山隧道", 10, Constants.TUNNEL));
//        geocodes.add(new Geocode("泸定县", 11, Constants.COUNTY));
//        geocodes.add(new Geocode("烹坝乡", 12, Constants.VILLAGE));
//        geocodes.add(new Geocode("冷竹关村", 13, Constants.VILLAGE));
//        geocodes.add(new Geocode("瓦斯沟", 14, Constants.VILLAGE));
//        geocodes.add(new Geocode("小天都隧道", 15, Constants.TUNNEL));
//        geocodes.add(new Geocode("康定", 16, Constants.CITY));
//        geocodes.add(new Geocode("折多塘村", 17, Constants.VILLAGE));
//        geocodes.add(new Geocode("新都桥镇", 18, Constants.TOWN));
//        geocodes.add(new Geocode("雅江县", 19, Constants.COUNTY));
//        geocodes.add(new Geocode("相克宗", 20, Constants.VILLAGE));
//        geocodes.add(new Geocode("红龙乡", 21, Constants.VILLAGE));
//        geocodes.add(new Geocode("理塘县", 23, Constants.COUNTY));
//        geocodes.add(new Geocode("德达隧道", 24, Constants.TUNNEL));
//        geocodes.add(new Geocode("列衣隧道", 25, Constants.TUNNEL));
//        geocodes.add(new Geocode("波戈溪隧道", 26, Constants.TUNNEL));
//        geocodes.add(new Geocode("拉纳山隧道", 27, Constants.TUNNEL));
//        geocodes.add(new Geocode("黄草坪1号隧道", 28, Constants.TUNNEL));
//        geocodes.add(new Geocode("黄草坪2号隧道", 29, Constants.TUNNEL));
//        geocodes.add(new Geocode("巴塘县", 30, Constants.COUNTY));
//        geocodes.add(new Geocode("竹巴龙乡", 31, Constants.VILLAGE));
//        geocodes.add(new Geocode("海通兵站", 32, Constants.OTHERS));
//        geocodes.add(new Geocode("芒康县", 33, Constants.COUNTY));
//        geocodes.add(new Geocode("如美镇", 34, Constants.TOWN));
//        geocodes.add(new Geocode("登巴村", 35,"芒康县", Constants.VILLAGE));
//        geocodes.add(new Geocode("左贡县", 36, Constants.COUNTY));
//        geocodes.add(new Geocode("田妥镇", 37, Constants.TOWN));
//        geocodes.add(new Geocode("斜库村", 38, Constants.VILLAGE));
//        geocodes.add(new Geocode("邦达镇", 39, Constants.TOWN));
//        geocodes.add(new Geocode("同尼村", 40, Constants.VILLAGE));
//        geocodes.add(new Geocode("八宿县", 41, Constants.COUNTY));
//
//        geocodes.add(new Geocode("吉达乡", 42, Constants.VILLAGE));
//        geocodes.add(new Geocode("然乌镇", 43, Constants.TOWN));
//        geocodes.add(new Geocode("宗坝村", 44, Constants.VILLAGE));
//        geocodes.add(new Geocode("松宗镇", 45, Constants.TOWN));
//        geocodes.add(new Geocode("波密县", 46, Constants.COUNTY));
//        geocodes.add(new Geocode("古乡", 47, Constants.VILLAGE));
//        geocodes.add(new Geocode("通麦大桥", 48, Constants.BRIDGE));
//        geocodes.add(new Geocode("林芝排龙村", 49, Constants.VILLAGE));
//        geocodes.add(new Geocode("林芝东久村", 50, Constants.VILLAGE));
//        geocodes.add(new Geocode("鲁朗镇", 51, Constants.TOWN));
//
//        geocodes.add(new Geocode("八一镇", 52, Constants.COUNTY));
//        geocodes.add(new Geocode("更张村", 53, Constants.VILLAGE));
//        geocodes.add(new Geocode("百巴镇", 54, Constants.TOWN));
//        geocodes.add(new Geocode("工布江达镇", 55, Constants.COUNTY));
//        geocodes.add(new Geocode("金达镇", 56, Constants.TOWN));
//        geocodes.add(new Geocode("加兴乡", 57, Constants.VILLAGE));
//        geocodes.add(new Geocode("日多乡", 58, Constants.VILLAGE));
//        geocodes.add(new Geocode("墨竹工卡县", 59, Constants.COUNTY));
//        geocodes.add(new Geocode("达孜县", 60, Constants.COUNTY));
//        geocodes.add(new Geocode("拉萨", 61, Constants.CITY));

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
