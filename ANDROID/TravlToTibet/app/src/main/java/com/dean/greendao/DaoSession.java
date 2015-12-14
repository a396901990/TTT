package com.dean.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.dean.greendao.Geocode;
import com.dean.greendao.Plan;
import com.dean.greendao.Route;
import com.dean.greendao.RoutePlan;
import com.dean.greendao.PrepareInfo;
import com.dean.greendao.PrepareDetail;
import com.dean.greendao.RecentRoute;
import com.dean.greendao.Hotel;
import com.dean.greendao.Scenic;

import com.dean.greendao.GeocodeDao;
import com.dean.greendao.PlanDao;
import com.dean.greendao.RouteDao;
import com.dean.greendao.RoutePlanDao;
import com.dean.greendao.PrepareInfoDao;
import com.dean.greendao.PrepareDetailDao;
import com.dean.greendao.RecentRouteDao;
import com.dean.greendao.HotelDao;
import com.dean.greendao.ScenicDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig geocodeDaoConfig;
    private final DaoConfig planDaoConfig;
    private final DaoConfig routeDaoConfig;
    private final DaoConfig routePlanDaoConfig;
    private final DaoConfig prepareInfoDaoConfig;
    private final DaoConfig prepareDetailDaoConfig;
    private final DaoConfig recentRouteDaoConfig;
    private final DaoConfig hotelDaoConfig;
    private final DaoConfig scenicDaoConfig;

    private final GeocodeDao geocodeDao;
    private final PlanDao planDao;
    private final RouteDao routeDao;
    private final RoutePlanDao routePlanDao;
    private final PrepareInfoDao prepareInfoDao;
    private final PrepareDetailDao prepareDetailDao;
    private final RecentRouteDao recentRouteDao;
    private final HotelDao hotelDao;
    private final ScenicDao scenicDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        geocodeDaoConfig = daoConfigMap.get(GeocodeDao.class).clone();
        geocodeDaoConfig.initIdentityScope(type);

        planDaoConfig = daoConfigMap.get(PlanDao.class).clone();
        planDaoConfig.initIdentityScope(type);

        routeDaoConfig = daoConfigMap.get(RouteDao.class).clone();
        routeDaoConfig.initIdentityScope(type);

        routePlanDaoConfig = daoConfigMap.get(RoutePlanDao.class).clone();
        routePlanDaoConfig.initIdentityScope(type);

        prepareInfoDaoConfig = daoConfigMap.get(PrepareInfoDao.class).clone();
        prepareInfoDaoConfig.initIdentityScope(type);

        prepareDetailDaoConfig = daoConfigMap.get(PrepareDetailDao.class).clone();
        prepareDetailDaoConfig.initIdentityScope(type);

        recentRouteDaoConfig = daoConfigMap.get(RecentRouteDao.class).clone();
        recentRouteDaoConfig.initIdentityScope(type);

        hotelDaoConfig = daoConfigMap.get(HotelDao.class).clone();
        hotelDaoConfig.initIdentityScope(type);

        scenicDaoConfig = daoConfigMap.get(ScenicDao.class).clone();
        scenicDaoConfig.initIdentityScope(type);

        geocodeDao = new GeocodeDao(geocodeDaoConfig, this);
        planDao = new PlanDao(planDaoConfig, this);
        routeDao = new RouteDao(routeDaoConfig, this);
        routePlanDao = new RoutePlanDao(routePlanDaoConfig, this);
        prepareInfoDao = new PrepareInfoDao(prepareInfoDaoConfig, this);
        prepareDetailDao = new PrepareDetailDao(prepareDetailDaoConfig, this);
        recentRouteDao = new RecentRouteDao(recentRouteDaoConfig, this);
        hotelDao = new HotelDao(hotelDaoConfig, this);
        scenicDao = new ScenicDao(scenicDaoConfig, this);

        registerDao(Geocode.class, geocodeDao);
        registerDao(Plan.class, planDao);
        registerDao(Route.class, routeDao);
        registerDao(RoutePlan.class, routePlanDao);
        registerDao(PrepareInfo.class, prepareInfoDao);
        registerDao(PrepareDetail.class, prepareDetailDao);
        registerDao(RecentRoute.class, recentRouteDao);
        registerDao(Hotel.class, hotelDao);
        registerDao(Scenic.class, scenicDao);
    }
    
    public void clear() {
        geocodeDaoConfig.getIdentityScope().clear();
        planDaoConfig.getIdentityScope().clear();
        routeDaoConfig.getIdentityScope().clear();
        routePlanDaoConfig.getIdentityScope().clear();
        prepareInfoDaoConfig.getIdentityScope().clear();
        prepareDetailDaoConfig.getIdentityScope().clear();
        recentRouteDaoConfig.getIdentityScope().clear();
        hotelDaoConfig.getIdentityScope().clear();
        scenicDaoConfig.getIdentityScope().clear();
    }

    public GeocodeDao getGeocodeDao() {
        return geocodeDao;
    }

    public PlanDao getPlanDao() {
        return planDao;
    }

    public RouteDao getRouteDao() {
        return routeDao;
    }

    public RoutePlanDao getRoutePlanDao() {
        return routePlanDao;
    }

    public PrepareInfoDao getPrepareInfoDao() {
        return prepareInfoDao;
    }

    public PrepareDetailDao getPrepareDetailDao() {
        return prepareDetailDao;
    }

    public RecentRouteDao getRecentRouteDao() {
        return recentRouteDao;
    }

    public HotelDao getHotelDao() {
        return hotelDao;
    }

    public ScenicDao getScenicDao() {
        return scenicDao;
    }

}
