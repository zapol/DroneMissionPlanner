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
    private Context context;


    public DbHelper(Context c)
    {
        super(c, DATABASE_NAME , null, 1);
        context = c;
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
        db.execSQL(
                "create table triggers " +
                        "(id                integer primary key," +
                        "name               text," +
                        "type               integer," +
                        "param              integer," +
                        "task               integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS missions");
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL("DROP TABLE IF EXISTS waypoints");
        db.execSQL("DROP TABLE IF EXISTS triggers");
        onCreate(db);
    }

    public long addMission(String name) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        if(missionExists(name)) throw new Exception(context.getResources().getText(R.string.missionNameExists).toString());
        else {
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            return db.insert("missions", null, cv);
        }
    }

    private boolean missionExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM missions WHERE name=\""+name+"\"", null);
        if(c.getCount()>0) return true;
        return false;
    }

    public long addTask(String name, int missionId) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        if(taskExists(name, missionId)) throw new Exception(context.getResources().getText(R.string.taskNameExists).toString());
        else {
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            return db.insert("tasks", null, cv);
        }
    }

    private boolean taskExists(String name, int missionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM tasks WHERE name=\""+name+"\" AND mission=\""+missionId+"\"", null);
        if(c.getCount()>0) return true;
        return false;
    }

    public Cursor getMissions()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name FROM missions ORDER BY name ASC", null);
    }

    public Cursor getTasks(long missionId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name FROM tasks WHERE mission="+missionId+" ORDER BY name ASC", null);
    }

    public void removeMission(long missionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("missions","id="+missionId,null);
    }

    public String getMissionName(long missionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM missions WHERE id="+missionId, null);
        return c.getString(0);
    }

    public Cursor getWaypoints() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name FROM waypoints ORDER BY name ASC", null);
    }

    public Cursor getTriggers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, name FROM triggers ORDER BY name ASC", null);
    }

    public String getTaskName(long taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM tasks WHERE id="+taskId, null);
        return c.getString(0);
    }

    public long getMissionFromTask(long taskId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT mission FROM tasks WHERE task="+taskId, null);
        return c.getInt(0);
    }

    public void removeTask(long taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks","id="+taskId,null);
    }
}