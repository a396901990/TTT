package com.daen.google;

import com.daen.google.module.Constants;
import com.daen.google.util.DataGeneratorUtil;

import java.util.ArrayList;

import com.daen.google.module.Geocode;

/**
 * Created by 95 on 2015/5/29.
 */
public class MyDataGenerator {
    private static ArrayList<Geocode> geocodes;

    public static void main(String[] args) throws Exception {

        geocodes = new ArrayList<>();


//        geocodes.add(new com.daen.google.module.Geocode("西宁", 0, com.daen.google.module.Constants.CITY));
//        geocodes.add(new com.daen.google.module.Geocode("湟源县", 1, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("倒淌河镇", 2, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("江西沟乡", 3, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("黑马河乡", 4, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("茶卡镇", 5, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("夏日哈镇", 6, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("都兰县", 7, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("香日德镇", 8, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("格尔木东收费站", 9, com.daen.google.module.Constants.CHECK_POINT));
//        geocodes.add(new com.daen.google.module.Geocode("格尔木市", 10, com.daen.google.module.Constants.CITY));
//        geocodes.add(new com.daen.google.module.Geocode("纳赤台", 11, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("不冻泉", 12, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("治多县五道梁", 13, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("唐古拉镇", 14, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("雁石坪", 15, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("安多县", 16, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("扎仁镇", 17, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("西藏自治区那曲", 18, com.daen.google.module.Constants.COUNTY));
//        geocodes.add(new com.daen.google.module.Geocode("罗玛镇", 19, com.daen.google.module.Constants.TOWN));
//        geocodes.add(new com.daen.google.module.Geocode("香茂乡", 20, com.daen.google.module.Constants.VILLAGE));
//        geocodes.add(new com.daen.google.module.Geocode("古露镇", 21, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("乌玛塘乡", 22, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("龙仁乡", 23, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("当雄县", 24, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("羊八井镇", 25, com.daen.google.module.Constants.TOWN));
        geocodes.add(new com.daen.google.module.Geocode("德庆乡", 26, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("古荣乡", 27, com.daen.google.module.Constants.VILLAGE));
        geocodes.add(new com.daen.google.module.Geocode("堆龙德庆县", 28, com.daen.google.module.Constants.COUNTY));
        geocodes.add(new com.daen.google.module.Geocode("拉萨", 29, com.daen.google.module.Constants.CITY));



        DataGeneratorUtil api = new DataGeneratorUtil();
        api.getData(geocodes);
    }
}
