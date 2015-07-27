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
        addRoute(schema);
        addZoneType(schema);
        addBuildingType(schema);

        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);

    }


    // 地理表
    private static void addGeocode(Schema schema) {
        Entity geocode = schema.addEntity("Geocode");
        geocode.addIdProperty();
        geocode.addStringProperty("name").notNull();
        geocode.addDoubleProperty("elevation").notNull();
        geocode.addDoubleProperty("mileage").notNull();
        geocode.addDoubleProperty("milestone").notNull();
        geocode.addDoubleProperty("distance").notNull();
        geocode.addDoubleProperty("latitude").notNull();
        geocode.addDoubleProperty("longitude").notNull();
        geocode.addStringProperty("address").notNull();
        geocode.addStringProperty("types").notNull();
        geocode.addStringProperty("road");
    }

    // 路线表
    private static void addRoute(Schema schema) {
        Entity route = schema.addEntity("Routes");
        route.addIdProperty();
        route.addStringProperty("name").notNull();
        route.addStringProperty("start").notNull();
        route.addStringProperty("end").notNull();
        route.addStringProperty("type").notNull();
        route.addStringProperty("guide");
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
