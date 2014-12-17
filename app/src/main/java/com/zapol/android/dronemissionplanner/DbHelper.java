package com.zapol.android.dronemissionplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zapol on 12/17/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table missions " +
                        "(id                integer primary key," +
                        "name               text)"
        );
        db.execSQL(
                "create table tasks " +
                        "(id                integer primary key," +
                        "name               text," +
                        "mission            integer," +
                        "type               integer," +
                        "waypoint_bearing   integer," +
                        "v_type             integer," +
                        "v_param1           integer," +
                        "v_param2           integer," +
                        "finish_cond_type   integer," +
                        "finish_cond_param  integer," +
                        "next_task          integer)"
        );
        db.execSQL(
                "create table waypoints " +
                        "(id                integer primary key," +
                        "name               text," +
                        "lat                integer," +
                        "lon                integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS missions");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS waypoints");
        onCreate(db);
    }

    public long addMission(String name) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        if(missionExists(name)) throw new Exception("Mission with given name already exists");
        else {
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            return db.insert("missions", null, cv);
        }
    }

    private boolean missionExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query (true, "missions", null, "name="+name, null, null, null, null, null, null);
        if(c.getCount()>0) return true;
        return false;
    }

    public long addTask(String name) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        if(missionExists(name)) throw new Exception("Mission with given name already exists");
        else {
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            return db.insert("tasks", null, cv);
        }
    }

    private boolean taskExists(String name, int missionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query (true, "tasks", null, "name="+name+" AND mission="+missionId, null, null, null, null, null, null);
        if(c.getCount()>0) return true;
        return false;
    }
}