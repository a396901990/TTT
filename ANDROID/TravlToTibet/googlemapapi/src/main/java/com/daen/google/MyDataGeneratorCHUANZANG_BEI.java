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
public class MyDataGeneratorCHUANZANG_BEI {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<Geocode>();

        geocodes.add(new Geocode("成都", 0, Constants.CITY));
        geocodes.add(new Geocode("郫筒镇", 1, Constants.TOWN));
        geocodes.add(new Geocode("崇义镇", 2, Constants.TOWN));
        geocodes.add(new Geocode("聚源镇", 3, Constants.TOWN));
        geocodes.add(new Geocode("都江堰市", 4, Constants.CITY));
        geocodes.add(new Geocode("汶川马鞍石隧道", 6, Constants.TUNNEL));
        geocodes.add(new Geocode("汶川友谊隧道", 8, Constants.TUNNEL));
        geocodes.add(new Geocode("白云顶隧道", 5, Constants.TUNNEL));
        geocodes.add(new Geocode("映秀镇", 7, Constants.TOWN));
        geocodes.add(new Geocode("耿达乡", 9, Constants.VILLAGE));
        geocodes.add(new Geocode("汶川县卧龙镇", 10, Constants.TOWN));

        geocodes.add(new Geocode("花岩子隧道", 12, Constants.TUNNEL));
        geocodes.add(new Geocode("治多县日隆镇", 13, Constants.TOWN));
        geocodes.add(new Geocode("四姑娘山（南门）", 15, Constants.SCENIC_SPOT));
        geocodes.add(new Geocode("达维乡", 14, Constants.VILLAGE));
        geocodes.add(new Geocode("日尔乡", 16, Constants.VILLAGE));
        geocodes.add(new Geocode("沃日乡", 17, Constants.VILLAGE));
        geocodes.add(new Geocode("小金县", 18, Constants.COUNTY));
        geocodes.add(new Geocode("宅垄乡", 19, Constants.VILLAGE));
        geocodes.add(new Geocode("丹巴县", 20, Constants.COUNTY));

        geocodes.add(new Geocode("东谷乡", 21, Constants.COUNTY));
        geocodes.add(new Geocode("八美镇", 22, Constants.TOWN));
        geocodes.add(new Geocode("龙灯乡", 24, Constants.VILLAGE));
        geocodes.add(new Geocode("小金县松林口", 25, Constants.TOWN));
        geocodes.add(new Geocode("葛卡乡", 26, Constants.VILLAGE));
        geocodes.add(new Geocode("格西乡", 27, Constants.VILLAGE));
        geocodes.add(new Geocode("道孚县", 28, Constants.COUNTY));
        geocodes.add(new Geocode("麻孜乡", 29, Constants.VILLAGE));

        geocodes.add(new Geocode("仁达乡", 30, Constants.VILLAGE));
        geocodes.add(new Geocode("炉霍县", 31, Constants.COUNTY));
        geocodes.add(new Geocode("雅德乡", 32, Constants.VILLAGE));
        geocodes.add(new Geocode("朱倭乡", 33, Constants.VILLAGE));
        geocodes.add(new Geocode("充古乡", 34, Constants.VILLAGE));
        geocodes.add(new Geocode("庭卡村", 35, Constants.VILLAGE));

        geocodes.add(new Geocode("拖坝乡", 36, Constants.VILLAGE));
        geocodes.add(new Geocode("甘孜县", 37, Constants.COUNTY));
        geocodes.add(new Geocode("生康乡", 38, Constants.VILLAGE));
        geocodes.add(new Geocode("卡攻乡", 39, Constants.VILLAGE));

        geocodes.add(new Geocode("来马乡", 40, Constants.VILLAGE));
        geocodes.add(new Geocode("错阿乡", 41, Constants.VILLAGE));
        geocodes.add(new Geocode("马尼干戈乡", 42, Constants.VILLAGE));
        geocodes.add(new Geocode("新路海自然保护区", 43, Constants.SCENIC_SPOT));
        geocodes.add(new Geocode("柯洛洞乡", 44, Constants.VILLAGE));
        geocodes.add(new Geocode("德格县", 45, Constants.COUNTY));
        geocodes.add(new Geocode("龚垭乡", 46, Constants.VILLAGE));
        geocodes.add(new Geocode("岗托镇", 47, Constants.TOWN));
        geocodes.add(new Geocode("同普乡", 48, Constants.VILLAGE));
        geocodes.add(new Geocode("江达县", 49, Constants.COUNTY));

        geocodes.add(new Geocode("卡贡乡", 50, Constants.VILLAGE));
        geocodes.add(new Geocode("青泥洞乡", 51, Constants.VILLAGE));
        geocodes.add(new Geocode("玉龙镇", 52, Constants.TOWN));
        geocodes.add(new Geocode("妥坝乡", 53, Constants.VILLAGE));
        geocodes.add(new Geocode("嘎日村", 54, Constants.VILLAGE));
        geocodes.add(new Geocode("日通乡", 55, Constants.VILLAGE));
        geocodes.add(new Geocode("如意乡", 56, Constants.VILLAGE));
        geocodes.add(new Geocode("昌都", 57, Constants.CITY));
        geocodes.add(new Geocode("卡若镇", 58, Constants.TOWN));
        geocodes.add(new Geocode("吉塘镇", 59, Constants.TOWN));

        geocodes.add(new Geocode("邦达镇", 60, Constants.TOWN));
        geocodes.add(new Geocode("俄洛镇", 61, Constants.TOWN));
        geocodes.add(new Geocode("宾达乡", 62, Constants.VILLAGE));
        geocodes.add(new Geocode("桑多镇", 63, Constants.TOWN));
        geocodes.add(new Geocode("卡玛多乡", 64, Constants.VILLAGE));
        geocodes.add(new Geocode("觉恩乡", 65, Constants.VILLAGE));
        geocodes.add(new Geocode("沙贡乡", 66, Constants.VILLAGE));
        geocodes.add(new Geocode("丁青县夏拉村", 67, Constants.VILLAGE));
        geocodes.add(new Geocode("丁青县", 68, Constants.COUNTY));
        geocodes.add(new Geocode("色扎乡", 69, Constants.VILLAGE));

        geocodes.add(new Geocode("尺牍镇", 70, Constants.TOWN));
        geocodes.add(new Geocode("马色村", 71, Constants.VILLAGE));
        geocodes.add(new Geocode("巴达乡", 72, Constants.VILLAGE));
        geocodes.add(new Geocode("荣布镇", 73, Constants.TOWN));
        geocodes.add(new Geocode("巴青县雅安镇", 74, Constants.TOWN));
        geocodes.add(new Geocode("巴青县", 75, Constants.COUNTY));
        geocodes.add(new Geocode("索县", 76, Constants.COUNTY));
        geocodes.add(new Geocode("扎拉乡", 77, Constants.VILLAGE));
        geocodes.add(new Geocode("刚朵村", 78, Constants.VILLAGE));
        geocodes.add(new Geocode("夏曲镇", 79, Constants.TOWN));
        geocodes.add(new Geocode("达前乡", 80, Constants.VILLAGE));
        geocodes.add(new Geocode("孔玛乡", 81, Constants.VILLAGE));
        geocodes.add(new Geocode("那曲", 82, Constants.COUNTY));
        geocodes.add(new Geocode("罗玛镇", 83, Constants.TOWN));
        geocodes.add(new Geocode("香茂乡", 84, Constants.VILLAGE));
        geocodes.add(new Geocode("古露镇", 85, Constants.TOWN));
        geocodes.add(new Geocode("乌玛塘乡", 86, Constants.VILLAGE));
        geocodes.add(new Geocode("龙仁乡", 87, Constants.VILLAGE));
        geocodes.add(new Geocode("当雄县", 88, Constants.COUNTY));
        geocodes.add(new Geocode("宁中乡", 89, Constants.VILLAGE));
        geocodes.add(new Geocode("羊八井镇", 90, Constants.TOWN));
        geocodes.add(new Geocode("德庆乡", 91, Constants.VILLAGE));
        geocodes.add(new Geocode("古荣乡", 92, Constants.VILLAGE));
        geocodes.add(new Geocode("堆龙德庆县", 93, Constants.COUNTY));
        geocodes.add(new Geocode("拉萨", 94, Constants.CITY));

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
