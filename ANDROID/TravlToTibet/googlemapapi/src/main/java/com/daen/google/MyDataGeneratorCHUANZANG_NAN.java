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

//        geocodes.add(new Geocode("成都", 0,"四川省", Constants.CITY));
//        geocodes.add(new Geocode("新津县", 1, Constants.COUNTY));
//        geocodes.add(new Geocode("邛崃市", 2, Constants.CITY));
//        geocodes.add(new Geocode("名山区", 3, Constants.COUNTY));
//        geocodes.add(new Geocode("金鸡关隧道", 4, Constants.TUNNEL));
//        geocodes.add(new Geocode("雅安", 5, Constants.CITY));
//        geocodes.add(new Geocode("多营镇", 6, Constants.TOWN));
//        geocodes.add(new Geocode("飞仙关镇", 7, Constants.TOWN));
//        geocodes.add(new Geocode("天全县", 8, Constants.TOWN));
//        geocodes.add(new Geocode("紫石乡", 9, Constants.VILLAGE));
//        geocodes.add(new Geocode("二郎山隧道", 10, Constants.TUNNEL));
//        geocodes.add(new Geocode("泸定县", 11, Constants.CITY));

//        geocodes.add(new Geocode("泸定县", 11, Constants.CITY));
//        geocodes.add(new Geocode("烹坝乡", 12, Constants.VILLAGE));
//        geocodes.add(new Geocode("冷竹关村", 13, Constants.VILLAGE));
//        geocodes.add(new Geocode("瓦斯沟", 14, Constants.VILLAGE));
//        geocodes.add(new Geocode("小天都隧道", 15, Constants.TUNNEL));
//        geocodes.add(new Geocode("康定市", 16, Constants.CITY));
//        geocodes.add(new Geocode("折多塘村", 17, Constants.VILLAGE));
//        geocodes.add(new Geocode("新都桥镇", 18, Constants.TOWN));
//        geocodes.add(new Geocode("雅江县", 19, Constants.TOWN));
//
//        geocodes.add(new Geocode("雅江县", 19, Constants.COUNTY));
//        geocodes.add(new Geocode("相克宗", 20, Constants.VILLAGE));
//        geocodes.add(new Geocode("红龙乡", 21, Constants.VILLAGE));
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

        geocodes.add(new Geocode("鲁朗镇", 45, Constants.TOWN));
        geocodes.add(new Geocode("八一镇", 47, Constants.TOWN));
        geocodes.add(new Geocode("更张村", 48, Constants.VILLAGE));
        geocodes.add(new Geocode("百巴镇", 49, Constants.TOWN));
        geocodes.add(new Geocode("工布江达镇", 50, Constants.TOWN));
        geocodes.add(new Geocode("金达镇", 51, Constants.TOWN));
        geocodes.add(new Geocode("加兴乡", 52, Constants.VILLAGE));
        geocodes.add(new Geocode("日多乡", 54, Constants.VILLAGE));
        geocodes.add(new Geocode("墨竹工卡县", 55, Constants.COUNTY));
        geocodes.add(new Geocode("达孜县", 56, Constants.COUNTY));
        geocodes.add(new Geocode("拉萨", 57, Constants.CITY));

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
