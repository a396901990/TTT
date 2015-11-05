package com.dean.travltotibet.util;

public final class Constants {
    // 城市
    public static final int CITY = 1;

    // 县城
    public static final int COUNTY = 2;

    // 镇
    public static final int TOWN = 3;

    // 村
    public static final int VILLAGE = 4;

    // 山峰
    public static final int MOUNTAIN = 5;

    // 隧道
    public static final int TUNNEL = 6;

    // 桥梁
    public static final int BRIDGE = 7;

    // 风景区
    public static final int SCENIC_SPOT = 7;

    // 检查站
    public static final int CHECK_POINT = 8;

    // 旅店
    public static final int HOTEL = 9;

    // 商店
    public static final int STORE = 10;

    // 路径
    public static final int PATH = 0;

    public static final String NAME_HEIGHT = "海拔：";
    public static final String NAME_MILEAGE = "里程：";

    public static final String DB_NAME = "TTT_DB";

    // plan start end date
    public static final String INTENT_START = "intent_start";
    public static final String INTENT_END = "intent_end";
    public static final String INTENT_DATE = "intent_Date";
    public static final String INTENT_PLAN_BUNDLE = "intent_plan_bundle";
    public static final String INTENT_FROM_WHERE = "intent_from_where";

    // route info intent
    public static final String INTENT_ROUTE_BUNDLE = "intent_route_bundle";
    public static final String INTENT_ROUTE = "intent_route";
    public static final String INTENT_ROUTE_NAME = "intent_route_name";
    public static final String INTENT_ROUTE_PLAN_ID = "intent_route_plan_id";
    public static final String INTENT_ROUTE_TYPE = "intent_route_type";
    public static final String INTENT_ROUTE_DAY = "intent_route_day";
    public static final String INTENT_ROUTE_DIR = "intent_route_direction";
    public static final String INTENT_ROUTE_ORIENTATION = "intent_route_setting_orientation";

    // prepare intent
    public static final String INTENT_PREPARE_BUNDLE = "intent_prepare_bundle";
    public static final String INTENT_PREPARE_TYPE = "intent_prepare_type";

    public static final String DECIMAL_FORMAT = "###,##0.00";
    public static final String STRING_INTEGER_FORMATTER = "#0";
    public static final String FOUR_INTEGER_FORMATTER = "###0";

    public static final String GUIDE_OVERALL_HEIGHT_FORMAT = "海拔: %sM";
    public static final String GUIDE_OVERALL_MILESTONE_FORMAT = "里程碑: %s/%s";

    public static final String ROUTE_PLAN_DAY = "预计天数: %s天";

    public static final String HEADER_START_END= "%s-%s";
    public static final String HEADER_DISTANCE = "(%s)";
    public static final String HEADER_DAY = "DAY%s";

    public static final String BRIEF_DAY = "DAY%s / %s天";
    public static final String BRIEF_DAY_ROUTE = "%s / %s天";

    public static final String TRAVEL_TYPE_TITLE = "请选择旅行方式";

    public static final String RECENT_PLAN_NAME_DAY = "%s，%s天";

    /**
     * Keys to save RouteActivity status
     */
    public static final String ROUTE_ACTIVITY_CURRENT_PAGE_STATUS_KEY = "route_activity_current_page";

    public static final String ROUTE_ACTIVITY_PLAN_START_STATUS_KEY = "route_activity_plan_start";
    public static final String ROUTE_ACTIVITY_PLAN_END_STATUS_KEY = "route_activity_plan_end";
    public static final String ROUTE_ACTIVITY_PLAN_DATE_STATUS_KEY = "route_activity_plan_date";
    public static final String ROUTE_ACTIVITY_PLAN_DISTANCE_STATUS_KEY = "route_activity_plan_distance";
    public static final String ROUTE_ACTIVITY_PLAN_DESCRIBE_STATUS_KEY = "route_activity_plan_describe";
    public static final String ROUTE_ACTIVITY_PLAN_RANK_STATUS_KEY = "route_activity_plan_rank";

    public static final String CURRENT_POINTS = "current_points";
    public static final String ALL_POINTS = "all_points";
    public static final String POINT_DIVIDE_MARK = ",";
    public static final String POINT_DEFAULT = "default";
}
