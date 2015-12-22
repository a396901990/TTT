package com.dean.travltotibet.model;

import com.dean.greendao.PrepareInfo;
import com.dean.travltotibet.R;

import java.util.ArrayList;
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

    public static final Map<InfoType, String> INFO_NAME = new HashMap<InfoType, String>();

    public static final ArrayList<InfoType> BIKES = new ArrayList<InfoType>();
    public static final ArrayList<InfoType> HIKES = new ArrayList<InfoType>();
    public static final ArrayList<InfoType> MOTOS = new ArrayList<InfoType>();
    public static final ArrayList<InfoType> CARS = new ArrayList<InfoType>();

    static {

        BIKES.add(InfoType.BUDGET);
        BIKES.add(InfoType.MEDICINE);
        BIKES.add(InfoType.EQUIP_BIKE);
        BIKES.add(InfoType.CLOTHING);
        BIKES.add(InfoType.OUTDOOR_EQUIP);
        BIKES.add(InfoType.CREDENTIALS);
        BIKES.add(InfoType.PERSONAL);
        BIKES.add(InfoType.OTHER);

        HIKES.add(InfoType.BUDGET);
        HIKES.add(InfoType.MEDICINE);
        HIKES.add(InfoType.EQUIP_HIKE);
        HIKES.add(InfoType.CLOTHING);
        HIKES.add(InfoType.OUTDOOR_EQUIP);
        HIKES.add(InfoType.CREDENTIALS);
        HIKES.add(InfoType.PERSONAL);
        HIKES.add(InfoType.OTHER);

        MOTOS.add(InfoType.BUDGET);
        MOTOS.add(InfoType.MEDICINE);
        MOTOS.add(InfoType.EQUIP_MOTO);
        MOTOS.add(InfoType.CLOTHING);
        MOTOS.add(InfoType.OUTDOOR_EQUIP);
        MOTOS.add(InfoType.CREDENTIALS);
        MOTOS.add(InfoType.PERSONAL);
        MOTOS.add(InfoType.OTHER);

        CARS.add(InfoType.BUDGET);
        CARS.add(InfoType.MEDICINE);
        CARS.add(InfoType.EQUIP_CAR);
        CARS.add(InfoType.CLOTHING);
        CARS.add(InfoType.OUTDOOR_EQUIP);
        CARS.add(InfoType.CREDENTIALS);
        CARS.add(InfoType.PERSONAL);
        CARS.add(InfoType.OTHER);

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

        INFO_IMAGE.put(InfoType.ROUTE_DETAIL, R.drawable.icon_route_detail);
        INFO_IMAGE.put(InfoType.BUDGET, R.drawable.icon_budget);
        INFO_IMAGE.put(InfoType.MEDICINE, R.drawable.icon_medicine);
        INFO_IMAGE.put(InfoType.EQUIP_BIKE, R.drawable.icon_bike_white);
        INFO_IMAGE.put(InfoType.EQUIP_HIKE, R.drawable.icon_hike_white);
        INFO_IMAGE.put(InfoType.EQUIP_MOTO, R.drawable.icon_moto_white);
        INFO_IMAGE.put(InfoType.EQUIP_CAR, R.drawable.icon_car_white);
        INFO_IMAGE.put(InfoType.CLOTHING, R.drawable.icon_clothing);
        INFO_IMAGE.put(InfoType.OUTDOOR_EQUIP, R.drawable.icon_outdoor);
        INFO_IMAGE.put(InfoType.CREDENTIALS, R.drawable.icon_card);
        INFO_IMAGE.put(InfoType.PERSONAL, R.drawable.icon_person);
        INFO_IMAGE.put(InfoType.OTHER, R.drawable.icon_other);

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

        INFO_NAME.put(InfoType.ROUTE_DETAIL, "ROUTE_DETAIL");
        INFO_NAME.put(InfoType.BUDGET, "BUDGET");
        INFO_NAME.put(InfoType.MEDICINE, "MEDICINE");
        INFO_NAME.put(InfoType.EQUIP_BIKE, "EQUIP_BIKE");
        INFO_NAME.put(InfoType.EQUIP_HIKE, "EQUIP_HIKE");
        INFO_NAME.put(InfoType.EQUIP_MOTO, "EQUIP_MOTO");
        INFO_NAME.put(InfoType.EQUIP_CAR, "EQUIP_CAR");
        INFO_NAME.put(InfoType.CLOTHING, "CLOTHING");
        INFO_NAME.put(InfoType.OUTDOOR_EQUIP, "OUTDOOR_EQUIP");
        INFO_NAME.put(InfoType.CREDENTIALS, "CREDENTIALS");
        INFO_NAME.put(InfoType.PERSONAL, "PERSONAL");
        INFO_NAME.put(InfoType.OTHER, "OTHER");

    }

    public static String getInfoResult(InfoType type, PrepareInfo prepareInfo) {
        switch (type) {
            case ROUTE_DETAIL:
                return prepareInfo.getRoute_overview();
            case BUDGET:
                return prepareInfo.getBudget();
            case MEDICINE:
                return prepareInfo.getMedicine();
            case EQUIP_BIKE:
                return prepareInfo.getEquip();
            case EQUIP_HIKE:
                return prepareInfo.getEquip();
            case EQUIP_MOTO:
                return prepareInfo.getEquip();
            case EQUIP_CAR:
                return prepareInfo.getEquip();
            case CLOTHING:
                return prepareInfo.getClothing();
            case OUTDOOR_EQUIP:
                return prepareInfo.getOutdoor_equip();
            case CREDENTIALS:
                return prepareInfo.getCredential();
            case PERSONAL:
                return prepareInfo.getPersonal();
            case OTHER:
                return prepareInfo.getOther();
        }
        return null;
    }

    public static ArrayList<InfoType> getInfoTypes(String type) {
        if (type.equals(TravelType.BIKE)) {
            return BIKES;
        }
        else if (type.equals(TravelType.HIKE)) {
            return HIKES;
        }
        else if (type.equals(TravelType.MOTO)) {
            return MOTOS;
        }
        else if (type.equals(TravelType.CAR)) {
            return CARS;
        }
        return null;
    }
}
