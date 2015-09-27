package com.dean.travltotibet.model;

import com.dean.travltotibet.R;
import com.dean.travltotibet.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeanGuo on 9/28/15.
 */
public enum InfoType {
    ROUTE_DETAIL,   // 路线详情
    BUDGET,         // 预算
    MEDICINE,       // 药品
    EQUIP_BIKE,     // 装备_骑行
    EQUIP_HIKE,     // 装备_徒步
    EQUIP_MOTO,     // 装备_摩托
    EQUIP_CAR,      // 装备_自驾
    CLOTHING,       // 衣物
    OUTDOOR_EQUIP,  // 户外装备
    CREDENTIALS,    // 证件
    PERSONAL,       // 个人用品
    OTHER;          // 其他

    public static final Map<InfoType, String> INFO_TEXT = new HashMap<InfoType, String>();
    public static final Map<InfoType, Integer> INFO_IMAGE = new HashMap<InfoType, Integer>();
    public static final Map<InfoType, Integer> INFO_COLOR = new HashMap<InfoType, Integer>();

    static {
        INFO_TEXT.put(InfoType.ROUTE_DETAIL, "路线详情");
        INFO_TEXT.put(InfoType.BUDGET, "预算");
        INFO_TEXT.put(InfoType.MEDICINE, "药品");
        INFO_TEXT.put(InfoType.EQUIP_BIKE, "骑行装备");
        INFO_TEXT.put(InfoType.EQUIP_HIKE, "徒步装备");
        INFO_TEXT.put(InfoType.EQUIP_MOTO, "摩托装备");
        INFO_TEXT.put(InfoType.EQUIP_CAR, "自驾装备");
        INFO_TEXT.put(InfoType.CLOTHING, "衣物");
        INFO_TEXT.put(InfoType.OUTDOOR_EQUIP, "户外装备");
        INFO_TEXT.put(InfoType.CREDENTIALS, "证件");
        INFO_TEXT.put(InfoType.PERSONAL, "个人物品");
        INFO_TEXT.put(InfoType.OTHER, "其他");

        INFO_IMAGE.put(InfoType.ROUTE_DETAIL, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.BUDGET, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.MEDICINE, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.EQUIP_BIKE, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.EQUIP_HIKE, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.EQUIP_MOTO, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.EQUIP_CAR, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.CLOTHING, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.OUTDOOR_EQUIP, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.CREDENTIALS, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.PERSONAL, R.drawable.bike_active);
        INFO_IMAGE.put(InfoType.OTHER, R.drawable.bike_active);

        INFO_COLOR.put(InfoType.ROUTE_DETAIL, R.color.orange_red);
        INFO_COLOR.put(InfoType.BUDGET, R.color.light_blue);
        INFO_COLOR.put(InfoType.MEDICINE, R.color.brown);
        INFO_COLOR.put(InfoType.EQUIP_BIKE, R.color.light_lime);
        INFO_COLOR.put(InfoType.EQUIP_HIKE, R.color.light_lime);
        INFO_COLOR.put(InfoType.EQUIP_MOTO, R.color.light_lime);
        INFO_COLOR.put(InfoType.EQUIP_CAR, R.color.light_lime);
        INFO_COLOR.put(InfoType.CLOTHING, R.color.sky_blue);
        INFO_COLOR.put(InfoType.OUTDOOR_EQUIP, R.color.gray);
        INFO_COLOR.put(InfoType.CREDENTIALS, R.color.light_green);
        INFO_COLOR.put(InfoType.PERSONAL, R.color.orange_red);
        INFO_COLOR.put(InfoType.OTHER, R.color.teal);

    }
}
