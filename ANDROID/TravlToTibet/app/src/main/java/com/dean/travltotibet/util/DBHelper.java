package com.dean.travltotibet.util;

import android.content.Context;

import com.dean.greendao.GeocodeDao.Properties;
import com.dean.travltotibet.TTTApplication;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dean on 2015/5/27.
 */
public class DBHelper
{
    private static Context mContext;
    private static DBHelper instance;

    private GeocodeDao geocodeDao;

    private DBHelper()
    {
    }

    public static DBHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DBHelper();
            if (mContext == null)
            {
                mContext = context;
            }

            // 数据库对象
            DaoSession daoSession = TTTApplication.getDaoSession(mContext);
            instance.geocodeDao = daoSession.getGeocodeDao();
        }
        return instance;
    }

    /** 查询 */
    public List<Geocode> getGeocodeList()
    {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        return qb.list();
    }

    /** 查询 */
    public List<Geocode> getGeocode()
    {
        return geocodeDao.loadAll();
    }

    /** 查询 */
    public boolean isSaved(int Id)
    {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Id.eq(Id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;// 查找收藏表
    }

    /** 删除 */
    public void deleteGeocodeList(int Id)
    {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        DeleteQuery<Geocode> bd = qb.where(Properties.Id.eq(Id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    /** 删除 */
    public void cleaGeocode()
    {
        geocodeDao.deleteAll();
    }

    /** 多重查询 */
    public List<Geocode> getLoactionsByRoad(String road)
    {
        QueryBuilder<Geocode> qb = geocodeDao.queryBuilder();
        qb.where(Properties.Name.eq(road));
        qb.orderAsc(Properties.Mileage);// 排序依据
        return qb.list();
    }
}