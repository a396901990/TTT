package com.dean.travltotibet.database;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.dean.greendao.DaoSession;
import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeDao;
import com.dean.greendao.GeocodeDao.Properties;
import com.dean.greendao.GeocodeOld;
import com.dean.greendao.Plan;
import com.dean.greendao.PlanDao;
import com.dean.greendao.RecentRoute;
import com.dean.greendao.RecentRouteDao;
import com.dean.greendao.Route;
import com.dean.greendao.RouteDao;
import com.dean.greendao.RoutePlan;
import com.dean.greendao.RoutePlanDao;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Location;
import com.dean.travltotibet.util.AppUtil;
import com.dean.travltotibet.util.Constants;
import com.dean.travltotibet.util.MapUtil;
import com.dean.travltotibet.util.StringUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dean on 2015/5/27.
 */
public class DBHelper {
    private static Context mContext;
    private static DBHelper instance;

    private GeocodeDao geocodeDao;

    private RouteDao routeDao;

    private PlanDao planDao;

    private RoutePlanDao routePlanDao;

    private RecentRouteDao recentRouteDao;

    private DBHelper() {
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (mContext == null) {
                mContext = context;
            }

            // 数据库对象
            DaoSession daoSession = TTTApplication.getDaoSession(mContext);
            instance.geocodeDao = daoSession.getGeocodeDao();
            instance.routeDao = daoSession.getRouteDao();
            instance.planDao = daoSession.getPlanDao();
            instance.routePlanDao = daoSession.getRoutePlanDao();
            instance.recentRouteDao = daoSession.getRecentRouteDao();
        }
        return instance;
    }

    /**
     * 根据路线，查询两个名字之间的地理位置信息
     */
    public List<Geocode> getGeocodeListWithNameAndRoute(String route, String start, String end, boolean isForward) {
        // 取出起始点id
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        // 取出终点id
        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        // 根据正反取出起点终点间的数据
        qb = geocodeDao.queryBuilder();
        if (startID < endID) {
            qb.where(Properties.Id.between(startID, endID));
        } else {
            qb.where(Properties.Id.between(endID, startID));
        }

        // 根据正反排序
        if (isForward) {
            qb.orderAsc(Properties.Id);
        } else {
            qb.orderDesc(Properties.Id);
        }

        return qb.list();
    }

    /**
     * 查询两个名字之间的地理位置信息
     */
    public List<Geocode> getNonPathGeocodeListWithNameAndRoute(String route, String start, String end, boolean isForward) {
        // 取出起始点id
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        // 取出终点id
        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        // 取出起点终点间不是path的数据
        qb = geocodeDao.queryBuilder();
        if (startID < endID) {
            qb.where(Properties.Id.between(startID, endID));
        } else {
            qb.where(Properties.Id.between(endID, startID));
        }

        qb.where(Properties.Types.notEq("PATH"));

        // 根据正反排序
        if (isForward) {
            qb.orderAsc(Properties.Id);
        } else {
            qb.orderDesc(Properties.Id);
        }

        return qb.list();
    }

    /**
     * 查询两个名字之间的地理位置信息
     */
    public List<Geocode> getNonPathMapGeocodeListWithNameAndRoute(String route, String start, String end, boolean isForward) {
        // 取出起始点id
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        // 取出终点id
        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        // 取出起点终点间不是path的数据
        qb = geocodeDao.queryBuilder();
        if (startID < endID) {
            qb.where(Properties.Id.between(startID, endID));
        } else {
            qb.where(Properties.Id.between(endID, startID));
        }

        qb.where(Properties.Types.notEq("PATH"));
        qb.where(Properties.Types.notEq("MAP"));

        // 根据正反排序
        if (isForward) {
            qb.orderAsc(Properties.Id);
        } else {
            qb.orderDesc(Properties.Id);
        }

        return qb.list();
    }

    /**
     * 根据name获取海拔
     *
     * @param name
     * @return
     */
    public Double getElevationWithName(String name) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(name));
        return qb.list().get(0).getElevation();
    }

    /**
     * 获取route距离
     */
    public String getDistanceBetweenTwoPoint(String route, String start, String end, boolean isForward) {
        // 取出起始点id
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        // 取出终点id
        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Route.eq(route));
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        // 根据正反取出起点终点间的数据
        qb = geocodeDao.queryBuilder();
        if (startID < endID) {
            qb.where(Properties.Id.between(startID, endID));
        } else {
            qb.where(Properties.Id.between(endID, startID));
        }

        // 根据正反排序
        if (isForward) {
            qb.orderAsc(Properties.Id);
        } else {
            qb.orderDesc(Properties.Id);
        }

        List<Geocode> geocodes = qb.list();
        double mileage = 0;

        for (int i = 0; i < geocodes.size(); i++) {
            Geocode geocode = geocodes.get(i);

            // 最后一个位置不需要进行计算，根据距离计算每个点得距离长度
            if (i < geocodes.size() - 2) {
                double distance = isForward ? geocode.getF_distance() : geocode.getR_distance();
                mileage = mileage + distance;
            }
        }

        return StringUtil.formatDoubleToInteger(mileage);
    }

    /**
     * 根据name获取经纬度信息为Location赋值
     *
     * @param name
     * @return
     */
    public Location getLocationWithName(String name) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(name));
        Geocode geocode = qb.list().get(0);

        return new Location(geocode.getLatitude(), geocode.getLongitude());
    }

    public Location getLocationWithGeocode(Geocode geocode) {
        Location location = MapUtil.google_bd_encrypt(geocode.getLatitude(), geocode.getLongitude());
        return location;
    }

    public LatLng getLatLngWithGeocode(Geocode geocode) {
        Location location = getLocationWithGeocode(geocode);
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    /**
     * 根据routeName获取路线信息
     */
    public Route getRouteInfo(String routeName, String routeType, boolean isForward) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(RouteDao.Properties.Route.eq(routeName));
//        qb.where(RouteDao.Properties.Type.eq(routeType));
        Route route = qb.list().get(0);

        // 创建一个新对象防止引用错误，根据正反设置起始和终点
        String start = route.getStart();
        String end = route.getEnd();
        Route currentRoute = new Route(route.getId(), route.getRoute(), route.getName(), route.getDay(), route.getStart(), route.getEnd(), route.getDistance(), route.getType(), route.getDescribe(), route.getDetail(), route.getPic_url(), route.getRank_hard(), route.getRank_view(), route.getRank_road());
        if (isForward) {
            currentRoute.setStart(start);
            currentRoute.setEnd(end);
        } else {
            currentRoute.setStart(end);
            currentRoute.setEnd(start);
        }

        return currentRoute;
    }

    /**
     * 获取方向中from得名字
     */
    public String getFromName(String routeName, boolean isForward) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(Properties.Route.eq(routeName));
        Route route = qb.list().get(0);
        return isForward ? route.getStart() : route.getEnd();
    }

    public String getFromName(String routeName, String FR) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(Properties.Route.eq(routeName));
        Route route = qb.list().get(0);
        return FR.equals("F") ? route.getStart() : route.getEnd();
    }

    /**
     * 获取方向中to得名字
     */
    public String getToName(String routeName, boolean isForward) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(Properties.Route.eq(routeName));
        Route route = qb.list().get(0);
        return isForward ? route.getEnd() : route.getStart();
    }

    public String getToName(String routeName, String FR) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(Properties.Route.eq(routeName));
        Route route = qb.list().get(0);
        return FR.equals("F") ? route.getEnd() : route.getStart();
    }

    /**
     * 根据name获取道路信息
     *
     * @param name
     * @return
     */
    public String getRoadWithName(String name) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(name));
        return qb.list().get(0).getRoad();
    }

    /**
     * 根据routeName,type,isForward获取routeplan数据
     */
    public List<RoutePlan> getRoutePlans(String routeName, String type, Boolean isForward) {
        QueryBuilder<RoutePlan> qb = routePlanDao.queryBuilder();
        qb.where(RoutePlanDao.Properties.Route.eq(routeName));
        qb.where(RoutePlanDao.Properties.Type.eq(type));
        qb.where(RoutePlanDao.Properties.Fr.eq(isForward ? "F" : "R"));
        return qb.list();
    }

    public RoutePlan getRoutePlanWithPlanID(String planID) {
        QueryBuilder<RoutePlan> qb = routePlanDao.queryBuilder();
        qb.where(RoutePlanDao.Properties.Id.eq(planID));
        return qb.list().get(0);
    }

    /**
     * 根据name获取里程碑
     */
    public String getMilestoneWithName(String name) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(name));
        return qb.list().get(0).getMilestone();
    }


    public String getRouteFullName(String routeName) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(RouteDao.Properties.Route.eq(routeName));
        return qb.list().get(0).getName();
    }

    /**
     * 查询所有路线信息
     */
    public List<Route> getRoutsList() {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        return qb.list();
    }

    /**
     * 获取route距离
     */
    public String getRouteDistance(String route) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(RouteDao.Properties.Route.eq(route));
        return qb.list().get(0).getDistance();
    }

    /**
     * 获取route详细介绍
     */
    public String getRouteDetail(String route) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(RouteDao.Properties.Route.eq(route));
        return qb.list().get(0).getDetail();
    }

    /**
     * 查询所有计划信息
     */
    public List<Plan> getPlanList(int routePlanId) {
        QueryBuilder<Plan> qb = planDao.queryBuilder();
        // 根据正反获取Plan
        qb.where(PlanDao.Properties.Route_plan_id.eq(routePlanId));
        return qb.list();
    }

    public List<Plan> getRoutePlanTypes(int routePlanId) {
        QueryBuilder<Plan> qb = planDao.queryBuilder();
        // 根据正反获取Plan
        qb.where(PlanDao.Properties.Route_plan_id.eq(routePlanId));
        return qb.list();
    }

    /**
     * 根据routePlanId查询planDays
     */
    public String getPlanDays(int routePlanId) {
        QueryBuilder<RoutePlan> qb = routePlanDao.queryBuilder();
        // 根据正反获取Plan
        qb.where(RoutePlanDao.Properties.Id.eq(routePlanId));
        return qb.list().get(0).getPlan_days();
    }

    /**
     * 根据route查询RoutePlanType
     */
    public String getRoutePlanType(String route) {
        QueryBuilder<RoutePlan> qb = routePlanDao.queryBuilder();
        // 根据正反获取Plan
        qb.where(RoutePlanDao.Properties.Route.eq(route));
        List<RoutePlan> routePlans = qb.list();

        StringBuffer stringBuffer = new StringBuffer();
        for (RoutePlan routePlan : routePlans) {
            stringBuffer.append(routePlan.getType());
        }
        return stringBuffer.toString();
    }

    /**
     * 获取路线图片
     */
    public String[] getRoutePics(String route) {
        QueryBuilder<Route> qb = routeDao.queryBuilder();
        qb.where(RouteDao.Properties.Route.eq(route));

        Route curRoute = qb.list().get(0);
        String pics = curRoute.getPic_url();

        return pics.split(Constants.URL_MARK);
    }

    /**
     * 查询
     */
    public List<Geocode> getGeocode() {
        return geocodeDao.loadAll();
    }

    /**
     * 查询
     */
    public boolean isSaved(int Id) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Id.eq(Id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;// 查找收藏表
    }

    /**
     * 检查是否存在相同，如果有相同的则删除
     */
    public void checkRecentRoute(RecentRoute recentRoute) {
        QueryBuilder<RecentRoute> qb = recentRouteDao.queryBuilder();
        qb.where(RecentRouteDao.Properties.Route.eq(recentRoute.getRoute()));
        qb.where(RecentRouteDao.Properties.Route_name.eq(recentRoute.getRoute_name()));
        qb.where(RecentRouteDao.Properties.Route_plan_id.eq(recentRoute.getRoute_plan_id()));
        qb.where(RecentRouteDao.Properties.FR.eq(recentRoute.getFR()));
        if (qb.buildCount().count() > 0) {
            recentRouteDao.deleteByKey(qb.list().get(0).getId());
        }
    }

    /**
     * 清空RecentRoute数据
     */
    public void cleanRecentRoutes() {
        recentRouteDao.deleteAll();
    }

    public void deleteRecentRoute(RecentRoute recentRoute) {
        recentRouteDao.delete(recentRoute);
    }

    /**
     * 插入数据到RecentRoute
     */
    public void insertRecentRoute(RecentRoute recentRoute) {
        recentRouteDao.insert(recentRoute);
    }

    /**
     * 获取RecentRoute数据，根据ID从大到小排列（逆序）
     *
     * @return
     */
    public List<RecentRoute> getRecentRoute() {
        QueryBuilder<RecentRoute> qb = recentRouteDao.queryBuilder();
        qb.orderDesc(RecentRouteDao.Properties.Id);
        return qb.list();
    }

    /**
     * 删除
     */
    public void deleteGeocodeList(int Id) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        DeleteQuery<Geocode> bd = qb.where(Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除Geocode数据
     */
    public void clearGeocode() {
        geocodeDao.deleteAll();
    }

    /**
     * 检查Geocode是否初始化
     */
    public boolean isGeocodeDaoInited() {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }

    /**
     * 初始化Geocode数据
     */
    public void initGeocodeData() {
        geocodeDao.deleteAll();
        if (!isGeocodeDaoInited()) {
            String json_result = ParseUtil.readFromRaw(mContext);
            Gson gson = new Gson();
            GeocodesJson geocodesJson = gson.fromJson(json_result, GeocodesJson.class);

            for (int i = 0; i < geocodesJson.getGeocodes().size(); i++) {
                GeocodeOld geocode = geocodesJson.getGeocodes().get(i);
                double r_distance;
                if (i == 0) {
                    r_distance = 0;
                } else {
                    r_distance = geocodesJson.getGeocodes().get(i - 1).getDistance();
                }
                Geocode g = new Geocode(geocode.getId(), "XINZANG", geocode.getName(), geocode.getElevation(),
                        geocode.getDistance(), r_distance, geocode.getLatitude(), geocode.getLongitude(),
                        geocode.getAddress(), geocode.getTypes(), geocode.getMilestone(),
                        geocode.getRoad(), "正向攻略", "反向攻略", "最后点攻略", "", "", ""
                );
                geocodeDao.insert(g);
            }
        }
    }

    public String getElevationWithNameString(String name) {
        return StringUtil.formatDoubleToInteger(getElevationWithName(name));
    }

    public String getRoadMileWithName(String name) {
        // 路线名
        String road = getRoadWithName(name);
        // 公里
        String milestone = getMilestoneWithName(name);

        return String.format(Constants.GUIDE_OVERALL_MILESTONE_WITHOUT_TITLE_FORMAT, road, milestone);
    }

    // 初始化读入数据库内容
    public String readDataBase(Context context) {

//        Toast.makeText(mContext, AppUtil.isNewVersion(mContext)+"", Toast.LENGTH_LONG).show();

        // 没数据或新版本时更新数据库
        if (!isGeocodeDaoInited() || AppUtil.isNewVersion(context)) {
            //if (true) {
            String DB_PATH = mContext.getDatabasePath(Constants.DB_NAME).getPath();
            //String DB_PATH = CommonData.baseDir + File.separator + Constants.DB_NAME;

            try {
                // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
                File f = new File(DB_PATH);
                // 如 database 目录不存在，新建该目录
                if (!f.exists()) {
                    f.mkdir();
                }
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                //InputStream is = context.getAssets().open(Constants.DB_NAME);
                InputStream is = context.getResources().openRawResource(R.raw.database);
                // 输出流
                OutputStream os = new FileOutputStream(DB_PATH);
                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                // 关闭文件流
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return DB_PATH;
        }
        return null;
    }

}