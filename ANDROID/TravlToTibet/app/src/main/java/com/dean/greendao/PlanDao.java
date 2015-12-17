package com.dean.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.dean.greendao.Plan;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PLAN.
*/
public class PlanDao extends AbstractDao<Plan, Long> {

    public static final String TABLENAME = "PLAN";

    /**
     * Properties of entity Plan.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Route_plan_id = new Property(1, String.class, "route_plan_id", false, "ROUTE_PLAN_ID");
        public final static Property Day = new Property(2, String.class, "day", false, "DAY");
        public final static Property Hours = new Property(3, String.class, "hours", false, "HOURS");
        public final static Property Start = new Property(4, String.class, "start", false, "START");
        public final static Property End = new Property(5, String.class, "end", false, "END");
        public final static Property Distance = new Property(6, String.class, "distance", false, "DISTANCE");
        public final static Property Describe = new Property(7, String.class, "describe", false, "DESCRIBE");
        public final static Property Rank_hard = new Property(8, String.class, "rank_hard", false, "RANK_HARD");
        public final static Property Rank_view = new Property(9, String.class, "rank_view", false, "RANK_VIEW");
        public final static Property Rank_road = new Property(10, String.class, "rank_road", false, "RANK_ROAD");
    };


    public PlanDao(DaoConfig config) {
        super(config);
    }
    
    public PlanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PLAN' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ROUTE_PLAN_ID' TEXT NOT NULL ," + // 1: route_plan_id
                "'DAY' TEXT NOT NULL ," + // 2: day
                "'HOURS' TEXT NOT NULL ," + // 3: hours
                "'START' TEXT NOT NULL ," + // 4: start
                "'END' TEXT NOT NULL ," + // 5: end
                "'DISTANCE' TEXT NOT NULL ," + // 6: distance
                "'DESCRIBE' TEXT NOT NULL ," + // 7: describe
                "'RANK_HARD' TEXT NOT NULL ," + // 8: rank_hard
                "'RANK_VIEW' TEXT NOT NULL ," + // 9: rank_view
                "'RANK_ROAD' TEXT NOT NULL );"); // 10: rank_road
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PLAN'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Plan entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getRoute_plan_id());
        stmt.bindString(3, entity.getDay());
        stmt.bindString(4, entity.getHours());
        stmt.bindString(5, entity.getStart());
        stmt.bindString(6, entity.getEnd());
        stmt.bindString(7, entity.getDistance());
        stmt.bindString(8, entity.getDescribe());
        stmt.bindString(9, entity.getRank_hard());
        stmt.bindString(10, entity.getRank_view());
        stmt.bindString(11, entity.getRank_road());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Plan readEntity(Cursor cursor, int offset) {
        Plan entity = new Plan( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // route_plan_id
            cursor.getString(offset + 2), // day
            cursor.getString(offset + 3), // hours
            cursor.getString(offset + 4), // start
            cursor.getString(offset + 5), // end
            cursor.getString(offset + 6), // distance
            cursor.getString(offset + 7), // describe
            cursor.getString(offset + 8), // rank_hard
            cursor.getString(offset + 9), // rank_view
            cursor.getString(offset + 10) // rank_road
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Plan entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRoute_plan_id(cursor.getString(offset + 1));
        entity.setDay(cursor.getString(offset + 2));
        entity.setHours(cursor.getString(offset + 3));
        entity.setStart(cursor.getString(offset + 4));
        entity.setEnd(cursor.getString(offset + 5));
        entity.setDistance(cursor.getString(offset + 6));
        entity.setDescribe(cursor.getString(offset + 7));
        entity.setRank_hard(cursor.getString(offset + 8));
        entity.setRank_view(cursor.getString(offset + 9));
        entity.setRank_road(cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Plan entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Plan entity) {
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
