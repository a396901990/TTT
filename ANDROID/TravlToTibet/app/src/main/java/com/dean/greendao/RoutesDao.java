package com.dean.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dean.greendao.Routes;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table ROUTES.
*/
public class RoutesDao extends AbstractDao<Routes, Long> {

    public static final String TABLENAME = "ROUTES";

    /**
     * Properties of entity Routes.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Start = new Property(2, String.class, "start", false, "START");
        public final static Property End = new Property(3, String.class, "end", false, "END");
        public final static Property Distance = new Property(4, String.class, "distance", false, "DISTANCE");
        public final static Property Type = new Property(5, String.class, "type", false, "TYPE");
        public final static Property Guide = new Property(6, String.class, "guide", false, "GUIDE");
    };


    public RoutesDao(DaoConfig config) {
        super(config);
    }
    
    public RoutesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'ROUTES' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'START' TEXT NOT NULL ," + // 2: start
                "'END' TEXT NOT NULL ," + // 3: end
                "'DISTANCE' TEXT NOT NULL ," + // 4: distance
                "'TYPE' TEXT NOT NULL ," + // 5: type
                "'GUIDE' TEXT);"); // 6: guide
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'ROUTES'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Routes entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        stmt.bindString(3, entity.getStart());
        stmt.bindString(4, entity.getEnd());
        stmt.bindString(5, entity.getDistance());
        stmt.bindString(6, entity.getType());
 
        String guide = entity.getGuide();
        if (guide != null) {
            stmt.bindString(7, guide);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Routes readEntity(Cursor cursor, int offset) {
        Routes entity = new Routes( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.getString(offset + 2), // start
            cursor.getString(offset + 3), // end
            cursor.getString(offset + 4), // distance
            cursor.getString(offset + 5), // type
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // guide
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Routes entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setStart(cursor.getString(offset + 2));
        entity.setEnd(cursor.getString(offset + 3));
        entity.setDistance(cursor.getString(offset + 4));
        entity.setType(cursor.getString(offset + 5));
        entity.setGuide(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Routes entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Routes entity) {
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
