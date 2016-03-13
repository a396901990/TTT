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

    public static final String DAO_PATH_DATA = "/Users/DeanGuo/Github_Workspace/ANDROID/TravlToTibet/datagenerate/src/main/java";
    public static final String PACKAGE_NAME_DATA = "com.example.deanguo.greendao";

    public static final int DATA_VERSION_CODE = 1;

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);
        addGeocode(schema);
        addPlan(schema);
        addRoute(schema);
        addRoutePlan(schema);
//        addPrepareInfo(schema);
        addRecentRoute(schema);
        addHotel(schema);
        addScenic(schema);

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
        geocode.addStringProperty("milestone");
        geocode.addStringProperty("road");
        geocode.addStringProperty("f_detail");
        geocode.addStringProperty("r_detail");
        geocode.addStringProperty("e_detail");
        geocode.addStringProperty("f_distance_point");
        geocode.addStringProperty("r_distance_point");
        geocode.addStringProperty("around_type");
    }

    // 计划表
    private static void addPlan(Schema schema) {
        Entity route = schema.addEntity("Plan");
        route.addIdProperty();
        route.addStringProperty("route_plan_id").notNull();
        route.addStringProperty("day").notNull();
        route.addStringProperty("hours").notNull();
        route.addStringProperty("start").notNull();
        route.addStringProperty("end").notNull();
        route.addStringProperty("distance").notNull();
        route.addStringProperty("describe").notNull();
        route.addStringProperty("rank_hard").notNull();
        route.addStringProperty("rank_view").notNull();
        route.addStringProperty("rank_road").notNull();
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
        route.addStringProperty("describe").notNull();
        route.addStringProperty("detail").notNull();
        route.addStringProperty("pic_url").notNull();
        route.addStringProperty("rank_hard").notNull();
        route.addStringProperty("rank_view").notNull();
        route.addStringProperty("rank_road").notNull();
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

    // 准备信息
    private static void addPrepareInfo(Schema schema) {
        Entity route = schema.addEntity("PrepareInfo");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("travel_type").notNull();
        route.addStringProperty("budget");
        route.addStringProperty("medicine");
        route.addStringProperty("equip");
        route.addStringProperty("clothing");
        route.addStringProperty("outdoor");
        route.addStringProperty("credential");
        route.addStringProperty("personal");
        route.addStringProperty("other");
        route.addStringProperty("attention");
    }

    // 记录最近路线
    private static void addRecentRoute(Schema schema) {
        Entity route = schema.addEntity("RecentRoute");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("route_name");
        route.addStringProperty("type");
        route.addStringProperty("FR");
        route.addStringProperty("route_plan_id");
        route.addStringProperty("plan_start");
        route.addStringProperty("plan_end");
    }

    // 记录最近路线
    private static void addHotel(Schema schema) {
        Entity route = schema.addEntity("Hotel");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("hotel_belong").notNull();
        route.addStringProperty("hotel_name").notNull();
        route.addStringProperty("hotel_address");
        route.addStringProperty("hotel_tel");
        route.addStringProperty("hotel_detail");
        route.addStringProperty("hotel_pic");
        route.addStringProperty("hotel_type");
    }

    // 记录最近路线
    private static void addScenic(Schema schema) {
        Entity route = schema.addEntity("Scenic");
        route.addIdProperty();
        route.addStringProperty("route").notNull();
        route.addStringProperty("scenic_name").notNull();
        route.addStringProperty("scenic_f_belong");
        route.addStringProperty("scenic_r_belong");
        route.addStringProperty("scenic_overview");
        route.addStringProperty("scenic_detail");
        route.addStringProperty("scenic_address");
        route.addStringProperty("scenic_f_traffic");
        route.addStringProperty("scenic_r_traffic");
        route.addStringProperty("scenic_opentime");
        route.addStringProperty("scenic_pic");
    }

}
