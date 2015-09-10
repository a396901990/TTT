package com.dean.travltotibet.database;

import android.content.Context;

import com.dean.greendao.DaoSession;
import com.dean.greendao.Geocode;
import com.dean.greendao.GeocodeDao;
import com.dean.greendao.GeocodeDao.Properties;
import com.dean.greendao.Plan;
import com.dean.greendao.PlanDao;
import com.dean.greendao.Route;
import com.dean.greendao.RouteDao;
import com.dean.greendao.Routes;
import com.dean.travltotibet.R;
import com.dean.travltotibet.TTTApplication;
import com.dean.travltotibet.model.Location;
import com.dean.travltotibet.util.Constants;
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

    private RouteDao routesDao;

    private PlanDao planDao;

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
            instance.routesDao = daoSession.getRouteDao();
            instance.planDao = daoSession.getPlanDao();
        }
        return instance;
    }

    /**
     * 查询所有地理位置信息
     */
    public List<Geocode> getGeocodeList() {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        return qb.list();
    }

    /**
     * 查询两个名字之间的地理位置信息
     */
    public List<Geocode> getGeocodeListWithName(String start, String end) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Id.between(startID, endID));
        return qb.list();
    }

    /**
     * 查询两个名字之间的地理位置信息
     */
    public List<Geocode> getNonPathGeocodeListWithName(String start, String end) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(start));
        long startID = qb.list().get(0).getId();

        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(end));
        long endID = qb.list().get(0).getId();

        qb = geocodeDao.queryBuilder();
        qb.where(Properties.Id.between(startID, endID));
        qb.where(Properties.Types.notEq("PATH"));

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
     * 根据name获取里程碑
     *
     * @param name
     * @return
     */
    public Double getMilestoneWithName(String name) {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(name));
        return qb.list().get(0).getMilestone();
    }

    /**
     * 查询所有路线信息
     */
    public List<Route> getRoutsList() {
        QueryBuilder<Route> qb = routesDao.queryBuilder();
        return qb.list();
    }

    /**
     * 查询所有计划信息
     */
    public List<Plan> getPlanList() {
        QueryBuilder<Plan> qb = planDao.queryBuilder();
        return qb.list();
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

        if (!isGeocodeDaoInited()) {
            String json_result = ParseUtil.readFromRaw(mContext);
            Gson gson = new Gson();
            GeocodesJson geocodesJson = gson.fromJson(json_result, GeocodesJson.class);

            for (Geocode geocode : geocodesJson.getGeocodes()) {
                geocodeDao.insert(geocode);
            }
        }
    }

    // 初始化读入数据库内容
    public String readDataBase(Context context) {

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

}