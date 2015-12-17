package com.example.deanguo.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.deanguo.greendao.RecentRoute;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RECENT_ROUTE.
*/
public class RecentRouteDao extends AbstractDao<RecentRoute, Long> {

    public static final String TABLENAME = "RECENT_ROUTE";

    /**
     * Properties of entity RecentRoute.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Route = new Property(1, String.class, "route", false, "ROUTE");
        public final static Property Route_name = new Property(2, String.class, "route_name", false, "ROUTE_NAME");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property FR = new Property(4, String.class, "FR", false, "FR");
        public final static Property Route_plan_id = new Property(5, String.class, "route_plan_id", false, "ROUTE_PLAN_ID");
        public final static Property Plan_start = new Property(6, String.class, "plan_start", false, "PLAN_START");
        public final static Property Plan_end = new Property(7, String.class, "plan_end", false, "PLAN_END");
    };


    public RecentRouteDao(DaoConfig config) {
        super(config);
    }
    
    public RecentRouteDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RECENT_ROUTE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ROUTE' TEXT NOT NULL ," + // 1: route
                "'ROUTE_NAME' TEXT," + // 2: route_name
                "'TYPE' TEXT," + // 3: type
                "'FR' TEXT," + // 4: FR
                "'ROUTE_PLAN_ID' TEXT," + // 5: route_plan_id
                "'PLAN_START' TEXT," + // 6: plan_start
                "'PLAN_END' TEXT);"); // 7: plan_end
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RECENT_ROUTE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RecentRoute entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRoute());
 
        String route_name = entity.getRoute_name();
        if (route_name != null) {
            stmt.bindString(3, route_name);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
 
        String FR = entity.getFR();
        if (FR != null) {
            stmt.bindString(5, FR);
        }
 
        String route_plan_id = entity.getRoute_plan_id();
        if (route_plan_id != null) {
            stmt.bindString(6, route_plan_id);
        }
 
        String plan_start = entity.getPlan_start();
        if (plan_start != null) {
            stmt.bindString(7, plan_start);
        }
 
        String plan_end = entity.getPlan_end();
        if (plan_end != null) {
            stmt.bindString(8, plan_end);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RecentRoute readEntity(Cursor cursor, int offset) {
        RecentRoute entity = new RecentRoute( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // route
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // route_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // FR
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // route_plan_id
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // plan_start
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // plan_end
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RecentRoute entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRoute(cursor.getString(offset + 1));
        entity.setRoute_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFR(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRoute_plan_id(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPlan_start(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPlan_end(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RecentRoute entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RecentRoute entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}