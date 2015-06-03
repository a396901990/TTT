package com.dean.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

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
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Elevation = new Property(2, double.class, "elevation", false, "ELEVATION");
        public final static Property Mileage = new Property(3, double.class, "mileage", false, "MILEAGE");
        public final static Property Latitude = new Property(4, double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(5, double.class, "longitude", false, "LONGITUDE");
        public final static Property Address = new Property(6, String.class, "address", false, "ADDRESS");
        public final static Property Types = new Property(7, String.class, "types", false, "TYPES");
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
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'ELEVATION' REAL NOT NULL ," + // 2: elevation
                "'MILEAGE' REAL NOT NULL ," + // 3: mileage
                "'LATITUDE' REAL NOT NULL ," + // 4: latitude
                "'LONGITUDE' REAL NOT NULL ," + // 5: longitude
                "'ADDRESS' TEXT NOT NULL ," + // 6: address
                "'TYPES' TEXT NOT NULL );"); // 7: types
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
        stmt.bindString(2, entity.getName());
        stmt.bindDouble(3, entity.getElevation());
        stmt.bindDouble(4, entity.getMileage());
        stmt.bindDouble(5, entity.getLatitude());
        stmt.bindDouble(6, entity.getLongitude());
        stmt.bindString(7, entity.getAddress());
        stmt.bindString(8, entity.getTypes());
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
            cursor.getString(offset + 1), // name
            cursor.getDouble(offset + 2), // elevation
            cursor.getDouble(offset + 3), // mileage
            cursor.getDouble(offset + 4), // latitude
            cursor.getDouble(offset + 5), // longitude
            cursor.getString(offset + 6), // address
            cursor.getString(offset + 7) // types
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Geocode entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setElevation(cursor.getDouble(offset + 2));
        entity.setMileage(cursor.getDouble(offset + 3));
        entity.setLatitude(cursor.getDouble(offset + 4));
        entity.setLongitude(cursor.getDouble(offset + 5));
        entity.setAddress(cursor.getString(offset + 6));
        entity.setTypes(cursor.getString(offset + 7));
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
