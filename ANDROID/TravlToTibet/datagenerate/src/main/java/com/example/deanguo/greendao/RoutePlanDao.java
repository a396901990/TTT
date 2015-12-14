package com.example.deanguo.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.deanguo.greendao.RoutePlan;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ROUTE_PLAN.
*/
public class RoutePlanDao extends AbstractDao<RoutePlan, Long> {

    public static final String TABLENAME = "ROUTE_PLAN";

    /**
     * Properties of entity RoutePlan.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Route = new Property(1, String.class, "route", false, "ROUTE");
        public final static Property Fr = new Property(2, String.class, "fr", false, "FR");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property Plan_name = new Property(4, String.class, "plan_name", false, "PLAN_NAME");
        public final static Property Plan_days = new Property(5, String.class, "plan_days", false, "PLAN_DAYS");
        public final static Property Describe = new Property(6, String.class, "describe", false, "DESCRIBE");
    };


    public RoutePlanDao(DaoConfig config) {
        super(config);
    }
    
    public RoutePlanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ROUTE_PLAN' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ROUTE' TEXT NOT NULL ," + // 1: route
                "'FR' TEXT NOT NULL ," + // 2: fr
                "'TYPE' TEXT NOT NULL ," + // 3: type
                "'PLAN_NAME' TEXT NOT NULL ," + // 4: plan_name
                "'PLAN_DAYS' TEXT NOT NULL ," + // 5: plan_days
                "'DESCRIBE' TEXT NOT NULL );"); // 6: describe
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ROUTE_PLAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RoutePlan entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRoute());
        stmt.bindString(3, entity.getFr());
        stmt.bindString(4, entity.getType());
        stmt.bindString(5, entity.getPlan_name());
        stmt.bindString(6, entity.getPlan_days());
        stmt.bindString(7, entity.getDescribe());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RoutePlan readEntity(Cursor cursor, int offset) {
        RoutePlan entity = new RoutePlan( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // route
            cursor.getString(offset + 2), // fr
            cursor.getString(offset + 3), // type
            cursor.getString(offset + 4), // plan_name
            cursor.getString(offset + 5), // plan_days
            cursor.getString(offset + 6) // describe
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RoutePlan entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRoute(cursor.getString(offset + 1));
        entity.setFr(cursor.getString(offset + 2));
        entity.setType(cursor.getString(offset + 3));
        entity.setPlan_name(cursor.getString(offset + 4));
        entity.setPlan_days(cursor.getString(offset + 5));
        entity.setDescribe(cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RoutePlan entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RoutePlan entity) {
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
