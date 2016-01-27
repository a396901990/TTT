package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.module.Geocode;
import com.daen.google.util.DataGeneratorUtil;

import java.util.ArrayList;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGeneratorHAINANDAO {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();
//
//        geocodes.add(new Geocode("海口市龙华区", 0, Constants.CITY));
//        geocodes.add(new Geocode("海口市灵山镇", 1, Constants.TOWN));
//        geocodes.add(new Geocode("海口市三江镇", 2, Constants.TOWN));
//        geocodes.add(new Geocode("大致坡镇", 3, Constants.TOWN));
//        geocodes.add(new Geocode("潭牛镇", 4, Constants.TOWN));
//        geocodes.add(new Geocode("文昌市", 5, Constants.CITY));
//        geocodes.add(new Geocode("文昌市陈家村", 6, Constants.VILLAGE));
//        geocodes.add(new Geocode("会文镇", 7, Constants.TOWN));
//        geocodes.add(new Geocode("琼海市长坡镇", 8, Constants.TOWN));
//        geocodes.add(new Geocode("博鳌镇", 9, Constants.TOWN));
//        geocodes.add(new Geocode("龙滚镇", 10, Constants.TOWN));
//        geocodes.add(new Geocode("万宁市", 11, Constants.CITY));
//        geocodes.add(new Geocode("兴隆温泉大道", 12, Constants.SCENIC_SPOT));
//        geocodes.add(new Geocode("陵水黎族自治县", 13, Constants.COUNTY));
//        geocodes.add(new Geocode("英州镇", 14, Constants.TOWN));
//        geocodes.add(new Geocode("海棠湾镇", 15, Constants.TOWN));
//        geocodes.add(new Geocode("亚龙湾", 16, Constants.TOWN));
//        geocodes.add(new Geocode("三亚市", 17, Constants.CITY));
//        geocodes.add(new Geocode("崖城镇", 18, Constants.TOWN));
//        geocodes.add(new Geocode("黄流镇", 19, Constants.TOWN));
//        geocodes.add(new Geocode("东方市板桥镇", 20, Constants.TOWN));
//        geocodes.add(new Geocode("东方市", 21, Constants.CITY));
        geocodes.add(new Geocode("东方市大田镇", 22, Constants.TOWN));
        geocodes.add(new Geocode("昌江黎族自治县", 23, Constants.COUNTY));
        geocodes.add(new Geocode("雅星镇", 24, Constants.TOWN));
        geocodes.add(new Geocode("儋州市", 25, Constants.CITY));
        geocodes.add(new Geocode("海口市秀英区", 26, Constants.CITY));

        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
