package com.dean.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 用来为GreenDao框架生成Dao文件
 */
public class MyGenerator {
    //public static final String DAO_PATH = "../app/src/main/java";
    public static final String DAO_PATH = "/Users/DeanGuo/Github_Workspace/ANDROID/TravlToTibet/app/src/main/java";
    public static final String PACKAGE_NAME = "com.dean.greendao";
    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addGeocode(schema);
        addPlan(schema);
        addRoute(schema);
        addRoutePlan(schema);
//        addZoneType(schema);
//        addBuildingType(schema);

        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }


    // 地理表
    private static void addGeocode(Schema schema) {
        Entity geocode = schema.addEntity("Geocode");
        geocode.addIdProperty();
        geocode.addStringProperty("route").notNull();
        geocode.addStringProperty("name").notNull();
        geocode.addDoubleProperty("elevation").notNull();
        geocode.addDoubleProperty("f_distance").notNull();
        geocode.addDoubleProperty("r_distance").notNull();
        geocode.addDoubleProperty("latitude").notNull();
        geocode.addDoubleProperty("longitude").notNull();
        geocode.addStringProperty("address").notNull();
        geocode.addStringProperty("types").notNull();
        geocode.addDoubleProperty("milestone").notNull();
        geocode.addStringProperty("road");
        geocode.addStringProperty("f_detail");
        geocode.addStringProperty("r_detail");
    }

    // 计划表
    private static void addPlan(Schema schema) {
        Entity route = schema.addEntity("Plan");
        route.addIdProperty();
        route.addStringProperty("route_plan_id").notNull();
        route.addStringProperty("day").notNull();
        route.addStringProperty("start").notNull();
        route.addStringProperty("end").notNull();
        route.addStringProperty("distance").notNull();
        route.addStringProperty("describe").notNull();
        route.addStringProperty("rank").notNull();
    }

    // 路线表
    private static void addRoute(Schema schema) {
        Entity route = schema.addEntity("Route");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("name").notNull();
        route.addStringProperty("day").notNull();
        route.addStringProperty("start").notNull();
        route.addStringProperty("end").notNull();
        route.addStringProperty("distance").notNull();
        route.addStringProperty("type").notNull();
        route.addStringProperty("rank").notNull();
        route.addStringProperty("describe").notNull();
        route.addStringProperty("detail").notNull();
        route.addStringProperty("pic_url").notNull();
    }


    // 路线_计划表
    private static void addRoutePlan(Schema schema) {
        Entity route = schema.addEntity("RoutePlan");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("fr").notNull();
        route.addStringProperty("type").notNull();
        route.addStringProperty("plan_name").notNull();
        route.addStringProperty("plan_days").notNull();
        route.addStringProperty("describe").notNull();
    }

    // 区域类型表
    private static void addZoneType(Schema schema) {
        Entity zone = schema.addEntity("ZoneType");
        zone.addIdProperty();
        zone.addStringProperty("CITY").notNull();
        zone.addStringProperty("COUNTY").notNull();
        zone.addStringProperty("TOWN").notNull();
        zone.addStringProperty("VILLAGE").notNull();
        zone.addStringProperty("DAOBAN").notNull();
        zone.addStringProperty("MOUNTAIN").notNull();
        zone.addStringProperty("SCENICSPOT").notNull();
        zone.addStringProperty("BUILDING").notNull();
        zone.addStringProperty("OTHERS").notNull();
    }

    // 建筑物类型表
    private static void addBuildingType(Schema schema) {
        Entity build = schema.addEntity("BuildingType");
        build.addIdProperty();
        build.addStringProperty("TUNNEL").notNull();
        build.addStringProperty("VIEW").notNull();
        build.addStringProperty("TENT").notNull();
        build.addStringProperty("HOTEL").notNull();
        build.addStringProperty("STORE").notNull();
        build.addStringProperty("CHECKPOINT").notNull();
        build.addStringProperty("BRIDGE").notNull();
    }

}
