package com.dean.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dean.greendao.Geocode;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table GEOCODE.
*/
public class GeocodeDao extends AbstractDao<Geocode, Long> {

    public static final String TABLENAME = "GEOCODE";

    /**
     * Properties of entity Geocode.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Route = new Property(1, String.class, "route", false, "ROUTE");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Elevation = new Property(3, double.class, "elevation", false, "ELEVATION");
        public final static Property F_distance = new Property(4, double.class, "f_distance", false, "F_DISTANCE");
        public final static Property R_distance = new Property(5, double.class, "r_distance", false, "R_DISTANCE");
        public final static Property Latitude = new Property(6, double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(7, double.class, "longitude", false, "LONGITUDE");
        public final static Property Address = new Property(8, String.class, "address", false, "ADDRESS");
        public final static Property Types = new Property(9, String.class, "types", false, "TYPES");
        public final static Property Milestone = new Property(10, String.class, "milestone", false, "MILESTONE");
        public final static Property Road = new Property(11, String.class, "road", false, "ROAD");
        public final static Property F_detail = new Property(12, String.class, "f_detail", false, "F_DETAIL");
        public final static Property R_detail = new Property(13, String.class, "r_detail", false, "R_DETAIL");
        public final static Property E_detail = new Property(14, String.class, "e_detail", false, "E_DETAIL");
        public final static Property F_distance_point = new Property(15, String.class, "f_distance_point", false, "F_DISTANCE_POINT");
        public final static Property R_distance_point = new Property(16, String.class, "r_distance_point", false, "R_DISTANCE_POINT");
        public final static Property Around_type = new Property(17, String.class, "around_type", false, "AROUND_TYPE");
    };


    public GeocodeDao(DaoConfig config) {
        super(config);
    }
    
    public GeocodeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'GEOCODE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ROUTE' TEXT NOT NULL ," + // 1: route
                "'NAME' TEXT NOT NULL ," + // 2: name
                "'ELEVATION' REAL NOT NULL ," + // 3: elevation
                "'F_DISTANCE' REAL NOT NULL ," + // 4: f_distance
                "'R_DISTANCE' REAL NOT NULL ," + // 5: r_distance
                "'LATITUDE' REAL NOT NULL ," + // 6: latitude
                "'LONGITUDE' REAL NOT NULL ," + // 7: longitude
                "'ADDRESS' TEXT NOT NULL ," + // 8: address
                "'TYPES' TEXT NOT NULL ," + // 9: types
                "'MILESTONE' TEXT," + // 10: milestone
                "'ROAD' TEXT," + // 11: road
                "'F_DETAIL' TEXT," + // 12: f_detail
                "'R_DETAIL' TEXT," + // 13: r_detail
                "'E_DETAIL' TEXT," + // 14: e_detail
                "'F_DISTANCE_POINT' TEXT," + // 15: f_distance_point
                "'R_DISTANCE_POINT' TEXT," + // 16: r_distance_point
                "'AROUND_TYPE' TEXT);"); // 17: around_type
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'GEOCODE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Geocode entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRoute());
        stmt.bindString(3, entity.getName());
        stmt.bindDouble(4, entity.getElevation());
        stmt.bindDouble(5, entity.getF_distance());
        stmt.bindDouble(6, entity.getR_distance());
        stmt.bindDouble(7, entity.getLatitude());
        stmt.bindDouble(8, entity.getLongitude());
        stmt.bindString(9, entity.getAddress());
        stmt.bindString(10, entity.getTypes());
 
        String milestone = entity.getMilestone();
        if (milestone != null) {
            stmt.bindString(11, milestone);
        }
 
        String road = entity.getRoad();
        if (road != null) {
            stmt.bindString(12, road);
        }
 
        String f_detail = entity.getF_detail();
        if (f_detail != null) {
            stmt.bindString(13, f_detail);
        }
 
        String r_detail = entity.getR_detail();
        if (r_detail != null) {
            stmt.bindString(14, r_detail);
        }
 
        String e_detail = entity.getE_detail();
        if (e_detail != null) {
            stmt.bindString(15, e_detail);
        }
 
        String f_distance_point = entity.getF_distance_point();
        if (f_distance_point != null) {
            stmt.bindString(16, f_distance_point);
        }
 
        String r_distance_point = entity.getR_distance_point();
        if (r_distance_point != null) {
            stmt.bindString(17, r_distance_point);
        }
 
        String around_type = entity.getAround_type();
        if (around_type != null) {
            stmt.bindString(18, around_type);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Geocode readEntity(Cursor cursor, int offset) {
        Geocode entity = new Geocode( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // route
            cursor.getString(offset + 2), // name
            cursor.getDouble(offset + 3), // elevation
            cursor.getDouble(offset + 4), // f_distance
            cursor.getDouble(offset + 5), // r_distance
            cursor.getDouble(offset + 6), // latitude
            cursor.getDouble(offset + 7), // longitude
            cursor.getString(offset + 8), // address
            cursor.getString(offset + 9), // types
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // milestone
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // road
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // f_detail
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // r_detail
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // e_detail
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // f_distance_point
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // r_distance_point
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17) // around_type
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Geocode entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRoute(cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setElevation(cursor.getDouble(offset + 3));
        entity.setF_distance(cursor.getDouble(offset + 4));
        entity.setR_distance(cursor.getDouble(offset + 5));
        entity.setLatitude(cursor.getDouble(offset + 6));
        entity.setLongitude(cursor.getDouble(offset + 7));
        entity.setAddress(cursor.getString(offset + 8));
        entity.setTypes(cursor.getString(offset + 9));
        entity.setMilestone(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setRoad(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setF_detail(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setR_detail(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setE_detail(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setF_distance_point(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setR_distance_point(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setAround_type(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Geocode entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Geocode entity) {
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
