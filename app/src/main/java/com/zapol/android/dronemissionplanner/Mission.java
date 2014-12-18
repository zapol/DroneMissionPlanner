package com.zapol.android.dronemissionplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

/**
 * Created by zapol on 16.12.14.
 */
public class Mission {
    private String name;
    private long id;
    private Vector <Task> tasks;

    public Mission(Context context, String name) throws Exception {
        if(name.isEmpty())
            throw new Exception(context.getResources().getText(R.string.missionNameEmpty).toString());

        DbHelper db = new DbHelper(context);
        db.addMission(name);
    }

    public Mission(Context context, long missionId)
    {
        DbHelper dbh = new DbHelper(context);
        SQLiteDatabase db = dbh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM missions WHERE id=\""+missionId+"\"", null);
        id = missionId;
        name = c.getString(0);

//        return db.getMission(id);
    }

    public Mission(){}
}
