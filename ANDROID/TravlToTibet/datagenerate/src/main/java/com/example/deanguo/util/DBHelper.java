package com.example.deanguo.util;

import android.content.Context;

import com.example.deanguo.datagenerate.MyApplication;
import com.example.deanguo.greendao.DaoSession;
import com.example.deanguo.greendao.Geocode;
import com.example.deanguo.greendao.GeocodeDao;
import com.google.gson.Gson;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Dean on 2015/5/27.
 */
public class DBHelper {
    private static Context mContext;
    private static DBHelper instance;

    private GeocodeDao geocodeDao;

    private DBHelper() {
    }

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (mContext == null) {
                mContext = context;
            }

            // 数据库对象
            DaoSession daoSession = MyApplication.getDaoSession(mContext);
            instance.geocodeDao = daoSession.getGeocodeDao();
        }
        return instance;
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
        //if (!isGeocodeDaoInited()) {
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
                Geocode g = new Geocode(geocode.getId(), "DIANZANG", geocode.getName(), geocode.getElevation(),
                        geocode.getDistance(), r_distance, geocode.getLatitude(), geocode.getLongitude(),
                        geocode.getAddress(), geocode.getTypes(), geocode.getMilestone(),
                        geocode.getRoad(), "正向攻略", "反向攻略"
                );
                geocodeDao.insert(g);
            }
        //}
    }

}