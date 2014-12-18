package com.zapol.android.dronemissionplanner;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * Created by zapol on 12/18/14.
 */
public class MissionCursorAdapter extends SimpleCursorAdapter {

    public MissionCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        notifyDataSetChanged();
    }
}
