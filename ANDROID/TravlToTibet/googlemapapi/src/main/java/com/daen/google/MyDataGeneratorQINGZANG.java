package com.daen.google;

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
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("西宁", 0, Constants.CITY));
        geocodes.add(new Geocode("湟源县", 1, Constants.COUNTY));
        geocodes.add(new Geocode("倒淌河镇", 2, Constants.COUNTY));
        geocodes.add(new Geocode("江西沟乡", 3, Constants.VILLAGE));
        geocodes.add(new Geocode("黑马河乡", 4, Constants.VILLAGE));
        geocodes.add(new Geocode("茶卡镇", 5, Constants.TOWN));
        geocodes.add(new Geocode("夏日哈镇", 6, Constants.TOWN));
        geocodes.add(new Geocode("都兰县", 7, Constants.COUNTY));
        geocodes.add(new Geocode("香日德镇", 8, Constants.TOWN));
        geocodes.add(new Geocode("格尔木东收费站", 9, Constants.CHECK_POINT));
        geocodes.add(new Geocode("格尔木市", 10, Constants.CITY));
        geocodes.add(new Geocode("纳赤台", 11, Constants.TOWN));
        geocodes.add(new Geocode("不冻泉", 12, Constants.TOWN));
        geocodes.add(new Geocode("五道梁", 13,"治多县", Constants.TOWN));
        geocodes.add(new Geocode("唐古拉镇", 14, Constants.TOWN));
        geocodes.add(new Geocode("雁石坪", 15, Constants.TOWN));
        geocodes.add(new Geocode("安多县", 16, Constants.COUNTY));
        geocodes.add(new Geocode("扎仁镇", 17, Constants.TOWN));
        geocodes.add(new Geocode("那曲", 18,"西藏自治区", Constants.COUNTY));
        geocodes.add(new Geocode("罗玛镇", 19, Constants.TOWN));
        geocodes.add(new Geocode("香茂乡", 20, Constants.VILLAGE));
        geocodes.add(new Geocode("古露镇", 21, Constants.TOWN));
        geocodes.add(new Geocode("乌玛塘乡", 22, Constants.VILLAGE));
        geocodes.add(new Geocode("龙仁乡", 23, Constants.VILLAGE));
        geocodes.add(new Geocode("当雄县", 24, Constants.COUNTY));
        geocodes.add(new Geocode("羊八井镇", 25, Constants.TOWN));
        geocodes.add(new Geocode("德庆乡", 26, Constants.VILLAGE));
        geocodes.add(new Geocode("古荣乡", 27, Constants.VILLAGE));
        geocodes.add(new Geocode("堆龙德庆县", 28, Constants.COUNTY));
        geocodes.add(new Geocode("拉萨", 29, Constants.CITY));

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
                        ArrayList<Geocode> mGeos = getAllData(geocodes);
                        removeMultiple(mGeos);
                        logData(mGeos);
                        ParseJson.parseToFile(mGeos);
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

    private static void logData(ArrayList<Geocode> mGeos) {
        Iterator<Geocode> it = mGeos.iterator();
        while(it.hasNext()){
            Geocode geo = it.next();
            System.out.println(geo.getName() + ":              高度："+geo.getElevation()+"                   距离"+geo.getDistance());
        }
    }

    private static void removeMultiple(ArrayList<Geocode> geocodes) {
        Iterator<Geocode> it = geocodes.iterator();
        while(it.hasNext()){
            Geocode geo = it.next();

            if(geo.getDistance()==0){
                it.remove();
            }
        }
        Collections.sort(geocodes, Geocode.MileageComparator);
    }

    /**
     * 新数据加文件数据排序后一起返回
     * @param geocodes
     * @return
     */
    private static ArrayList<Geocode> getAllData(ArrayList<Geocode> geocodes) {
        String json_result = ParseJson.readFile(ParseJson.OUTPUT_FILE_PATH_MAC);
        if (json_result == null || json_result.equals("")) {
            return geocodes;
        }
        Gson gson = new Gson();
        GeocodesJson geocodesJson = gson.fromJson(json_result, GeocodesJson.class);

        if (geocodesJson.getGeocodes().size() != 0) {
            geocodes.addAll(geocodesJson.getGeocodes());
        }

        return geocodes;
    }
}
