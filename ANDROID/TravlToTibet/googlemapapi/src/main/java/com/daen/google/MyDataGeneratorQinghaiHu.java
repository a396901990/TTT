package com.daen.google;

import com.daen.google.module.GeocodesJson;
import com.daen.google.runnable.DetailsInfoRunnable;
import com.daen.google.runnable.PathRunnable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorQinghaiHu {
    private static ArrayList<com.daen.google.module.Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<com.daen.google.module.Geocode>();


        geocodes.add(new com.daen.google.module.Geocode("西海镇", 3, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("江西沟乡", 4, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("黑马河乡", 5, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("石乃亥乡", 6, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("泉吉乡", 7, com.daen.google.module.Constants.CITY));
        geocodes.add(new com.daen.google.module.Geocode("刚察县", 8, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("哈尔盖乡", 9, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("甘子河乡", 10, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("西海镇", 27, com.daen.google.module.Constants.VILLAGE));


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
                        ArrayList<com.daen.google.module.Geocode> mGeos = getAllData(geocodes);
                        removeMultiple(mGeos);
                        logData(mGeos);
                        com.daen.google.util.ParseJson.parseToFile(mGeos);
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
        geocodes.add(new com.daen.google.module.Geocode("拉孜县柳乡", 30, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("江孜县", 34, com.daen.google.module.Constants.COUNTY));

        Thread t = new Thread(detailsInfoRunnable);
        t.start();
    }

    private static void logData(ArrayList<com.daen.google.module.Geocode> mGeos) {
        Iterator<com.daen.google.module.Geocode> it = mGeos.iterator();
        while(it.hasNext()){
            com.daen.google.module.Geocode geo = it.next();
            System.out.println(geo.getName() + ":              高度："+geo.getElevation()+"                   距离"+geo.getDistance());
        }
    }

    private static void removeMultiple(ArrayList<com.daen.google.module.Geocode> geocodes) {
        Iterator<com.daen.google.module.Geocode> it = geocodes.iterator();
        while(it.hasNext()){
            com.daen.google.module.Geocode geo = it.next();

            if(geo.getDistance()==0){
                it.remove();
            }
        }
        Collections.sort(geocodes, com.daen.google.module.Geocode.MileageComparator);
    }

    /**
     * 新数据加文件数据排序后一起返回
     * @param geocodes
     * @return
     */
    private static ArrayList<com.daen.google.module.Geocode> getAllData(ArrayList<com.daen.google.module.Geocode> geocodes) {
        String json_result = com.daen.google.util.ParseJson.readFile(com.daen.google.util.ParseJson.OUTPUT_FILE_PATH_MAC);
        if (json_result == null || json_result.equals("")) {
            return geocodes;
        }
        Gson gson = new Gson();
        com.daen.google.module.GeocodesJson geocodesJson = gson.fromJson(json_result, GeocodesJson.class);

        if (geocodesJson.getGeocodes().size() != 0) {
            geocodes.addAll(geocodesJson.getGeocodes());
        }

        return geocodes;
    }
}
