package com.zapol.android.dronemissionplanner;

import android.content.Context;

import java.util.Vector;

/**
 * Created by zapol on 16.12.14.
 */
public class Mission {
    private String name;
    private int id;
    private Vector <Task> tasks;

    public Mission(String name) throws Exception {
        if(name.isEmpty())
            
            throw new Exception("Mission name empty");

        DbHelper db = new DbHelper(Context.getApplicationContext());
    }

    public Mission(int id)
    {

    }
}
