package com.dean.travltotibet.ui.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DeanGuo on 11/3/15.
 */
public final class PointManager {

    // 起点，终点
    public static final String START_END = "START_END";
    // 城市
    public static final String CITY = "CITY";
    // 县城
    public static final String COUNTY = "COUNTY";
    // 镇
    public static final String TOWN = "TOWN";
    // 村
    public static final String VILLAGE = "VILLAGE";
    // 山峰
    public static final String MOUNTAIN = "MOUNTAIN";
    // 露营
    public static final String CAMP_SPOT = "CAMP_SPOT";
    // 隧道
    public static final String TUNNEL = "TUNNEL";
    // 桥梁
    public static final String BRIDGE = "BRIDGE";
    // 风景区
    public static final String SCENIC_SPOT = "SCENIC_SPOT";
    // 检查站
    public static final String CHECK_POINT = "CHECK_POINT";
    // 加油站
    public static final String GAS_STATION = "GAS_STATION";
    // 其他，兵站道班等
    public static final String OTHERS = "OTHERS";
    // 路径
    public static final String PATH = "PATH";

    public static final String MAP = "MAP";

    private static PointManager sInstance;

    private final Context mContext;

    public static void init(final Context context) {
        sInstance = new PointManager(context);
    }

    private PointManager(final Context context) {
        this.mContext = context;
    }

    // 标题
    private static final Map<String, Integer> TITLES = new HashMap<String, Integer>();

    static {
        TITLES.put(START_END, R.string.point_start_end);
        TITLES.put(CITY, R.string.point_city);
        TITLES.put(COUNTY, R.string.point_county);
        TITLES.put(TOWN, R.string.point_town);
        TITLES.put(VILLAGE, R.string.point_vallage);
        TITLES.put(MOUNTAIN, R.string.point_mountain);
        TITLES.put(CAMP_SPOT, R.string.point_camp);
        TITLES.put(SCENIC_SPOT, R.string.point_scenic);
        TITLES.put(CHECK_POINT, R.string.point_check_point);
        TITLES.put(TUNNEL, R.string.point_tunnel);
        TITLES.put(BRIDGE, R.string.point_bridge);
        TITLES.put(GAS_STATION, R.string.point_gas_station);
        TITLES.put(OTHERS, R.string.point_other);
    }

    // 颜色
    private static final Map<String, Integer> COLORS = new HashMap<String, Integer>();

    static {
        COLORS.put(START_END, R.color.red);
        COLORS.put(CITY, R.color.orange_red);
        COLORS.put(COUNTY, R.color.blue_gray);
        COLORS.put(TOWN, R.color.blue);
        COLORS.put(VILLAGE, R.color.dark_green);
        COLORS.put(MOUNTAIN, R.color.brown);
        COLORS.put(TUNNEL, R.color.light_red);
        COLORS.put(CAMP_SPOT, R.color.indigo);
        COLORS.put(SCENIC_SPOT, R.color.blue_gray);
        COLORS.put(CHECK_POINT, R.color.orange);
        COLORS.put(BRIDGE, R.color.green);
        COLORS.put(GAS_STATION, R.color.deep_purple);
        COLORS.put(OTHERS, R.color.lime);
    }

    // 颜色
    private static Map<String, PointDetailPaint> PAINTS;

    /**
     * 根据当前设置的显示点，初始化画笔
     *
     * @param pointCount 所有点的数量
     */
    public static void initPointDetailPaint(int pointCount) {
        PAINTS = new HashMap<String, PointDetailPaint>();

        String[] currentPoints = getCurrentPoints();

        PAINTS.put(PointManager.START_END, new PointDetailPaint(PointManager.START_END));

        for (int i = 0; i < currentPoints.length; i++) {
            String point = currentPoints[i];
            if (!TextUtils.isEmpty(point)) {
                PointDetailPaint pointDetailPaint = new PointDetailPaint(point);
                pointDetailPaint.setCount(pointCount);
                pointDetailPaint.setCurrentPointIndex(i);
                pointDetailPaint.setCurrentPointsSize(currentPoints.length);
                PAINTS.put(point, pointDetailPaint);
            }
        }
    }

    /**
     * 设置当前显示点
     *
     * @param points 当前点数组
     */
    public static void setCurrentPoints(String[] points) {

        SharedPreferences sp = TTTApplication.getSharedPreferences();
        StringBuffer sb = new StringBuffer();
        for (String point : points) {
            sb.append(point);
            sb.append(Constants.POINT_DIVIDE_MARK);
        }

        sp.edit().putString(Constants.CURRENT_POINTS, sb.toString()).commit();
    }

    /**
     * 获取当前显示点
     */
    public static String[] getCurrentPoints() {
        SharedPreferences sp = TTTApplication.getSharedPreferences();
        String pointsValue = sp.getString(Constants.CURRENT_POINTS, Constants.POINT_DEFAULT);
        if (!Constants.POINT_DEFAULT.equals(pointsValue)) {
            return pointsValue.split(Constants.POINT_DIVIDE_MARK);
        }

        return TTTApplication.getResourceUtil().getStringArray(R.array.default_points);
    }

    /**
     * 设置所有点
     *
     * @param points 所有点数组
     */
    public static void setAllPoints(String[] points) {

        SharedPreferences sp = TTTApplication.getSharedPreferences();
        StringBuffer sb = new StringBuffer();
        for (String point : points) {
            sb.append(point);
            sb.append(Constants.POINT_DIVIDE_MARK);
        }

        sp.edit().putString(Constants.ALL_POINTS, sb.toString()).commit();
    }

    /**
     * 获取所有点
     */
    public static String[] getAllPoints() {
        SharedPreferences sp = TTTApplication.getSharedPreferences();
        String pointsValue = sp.getString(Constants.ALL_POINTS, Constants.POINT_DEFAULT);
        if (!Constants.POINT_DEFAULT.equals(pointsValue)) {
            return pointsValue.split(Constants.POINT_DIVIDE_MARK);
        }

        return TTTApplication.getResourceUtil().getStringArray(R.array.default_all_points);
    }

    public static Map<String, PointDetailPaint> getPaints() {
        return PAINTS;
    }

    public static PointDetailPaint getPaint(String category) {
        return PAINTS.get(category);
    }

    public static Integer getTitle(String category) {
        return TITLES.get(category);
    }

    public static Map<String, Integer> getTitles() {
        return TITLES;
    }

    public static Integer getColor(String category) {
        return COLORS.get(category);
    }

    public static Map<String, Integer> getColors() {
        return COLORS;
    }
}
