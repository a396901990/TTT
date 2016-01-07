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
public class MyDataGeneratorQINGZANG {
    private static ArrayList<com.daen.google.module.Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<com.daen.google.module.Geocode>();

        geocodes.add(new com.daen.google.module.Geocode("西宁", 0, com.daen.google.module.Constants.CITY));
        geocodes.add(new com.daen.google.module.Geocode("湟源县", 1, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("倒淌河镇", 2, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("江西沟乡", 3, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("黑马河乡", 4, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("茶卡镇", 5, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("夏日哈镇", 6, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("都兰县", 7, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("香日德镇", 8, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("格尔木东收费站", 9, com.daen.google.module.Constants.CHECK_POINT));
        geocodes.add(new com.daen.google.module.Geocode("格尔木市", 10, com.daen.google.module.Constants.CITY));
        geocodes.add(new com.daen.google.module.Geocode("纳赤台", 11, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("不冻泉", 12, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("治多县五道梁", 13, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("唐古拉镇", 14, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("雁石坪", 15, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("安多县", 16, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("扎仁镇", 17, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("西藏自治区那曲", 18, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("罗玛镇", 19, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("香茂乡", 20, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("古露镇", 21, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("乌玛塘乡", 22, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("龙仁乡", 23, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("当雄县", 24, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("羊八井镇", 25, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("德庆乡", 26, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("古荣乡", 27, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("堆龙德庆县", 28, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("拉萨", 29, com.daen.google.module.Constants.CITY));

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
