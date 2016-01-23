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
public class MyDataGeneratorCHUANZANG_BEI {
    private static ArrayList<com.daen.google.module.Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<com.daen.google.module.Geocode>();

//        geocodes.add(new com.daen.google.module.Geocode("成都", 0, com.daen.google.module.Constants.CITY));
//        geocodes.add(new com.daen.google.module.Geocode("郫筒镇", 1, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("都江堰市", 4, com.daen.google.module.Constants.CITY));
//        geocodes.add(new com.daen.google.module.Geocode("汶川马鞍石隧道", 6, com.daen.google.module.Constants.TUNNEL));
//        geocodes.add(new com.daen.google.module.Geocode("汶川友谊隧道", 8, com.daen.google.module.Constants.TUNNEL));
//        geocodes.add(new com.daen.google.module.Geocode("白云顶隧道", 5, com.daen.google.module.Constants.TUNNEL));
//        geocodes.add(new com.daen.google.module.Geocode("映秀镇", 7, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("耿达乡", 9, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("汶川县卧龙镇", 10, com.daen.google.module.Constants.TOWN));
//
//        geocodes.add(new com.daen.google.module.Geocode("花岩子隧道", 12, com.daen.google.module.Constants.TUNNEL));
//        geocodes.add(new com.daen.google.module.Geocode("日隆镇", 13, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("四姑娘山（南门）", 14, com.daen.google.module.Constants.SCENIC_SPOT));
//        geocodes.add(new com.daen.google.module.Geocode("达维乡", 15, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("沃日乡", 17, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("小金县", 18, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("丹巴县", 20, com.daen.google.module.Constants.COUNTY));
//
//        geocodes.add(new com.daen.google.module.Geocode("东谷乡", 21, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("八美镇", 22, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("龙灯乡", 24, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("道孚县", 28, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("麻孜乡", 29, com.daen.google.module.Constants.VILLAGE));
//
//        geocodes.add(new com.daen.google.module.Geocode("仁达乡", 30, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("炉霍县", 31, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("雅德乡", 32, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("充古乡", 34, com.daen.google.module.Constants.VILLAGE));
//
//        geocodes.add(new com.daen.google.module.Geocode("拖坝乡", 36, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("甘孜藏族自治州甘孜县", 37, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("生康乡", 38, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("卡攻乡", 39, com.daen.google.module.Constants.VILLAGE));
//
//        geocodes.add(new com.daen.google.module.Geocode("错阿乡", 41, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("马尼干戈乡", 42, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("新路海自然保护区", 43, com.daen.google.module.Constants.SCENIC_SPOT));
//        geocodes.add(new com.daen.google.module.Geocode("柯洛洞乡", 44, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("德格县", 45, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("龚垭乡", 46, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("岗托镇", 47, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("同普乡", 48, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("江达县", 49, com.daen.google.module.Constants.COUNTY));
//
//        geocodes.add(new com.daen.google.module.Geocode("江达县卡贡乡", 50, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("青泥洞乡", 51, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("妥坝乡", 53, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("昌都嘎日村", 54, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("日通乡", 55, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("昌都", 57, com.daen.google.module.Constants.CITY));
//        geocodes.add(new com.daen.google.module.Geocode("俄洛镇", 61, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("宾达乡", 62, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("桑多镇", 63, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("卡玛多乡", 64, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("觉恩乡", 65, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("丁青县夏拉村", 67, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("丁青县", 68, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("色扎乡", 69, com.daen.google.module.Constants.VILLAGE));
//
//        geocodes.add(new com.daen.google.module.Geocode("尺牍镇", 70, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("丁青县马色村", 71, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("巴达乡", 72, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("荣布镇", 73, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("巴青县雅安镇", 74, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("巴青县", 75, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("索县", 76, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("扎拉乡", 77, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("刚朵村", 78, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("夏曲镇", 79, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("达前乡", 80, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("那曲", 82, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("罗玛镇", 83, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("香茂乡", 84, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("古露镇", 85, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("乌玛塘乡", 86, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("龙仁乡", 87, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("当雄县", 88, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("宁中乡", 89, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("羊八井镇", 90, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("德庆乡", 91, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("古荣乡", 92, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("堆龙德庆县", 93, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("拉萨", 94, com.daen.google.module.Constants.CITY));

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
