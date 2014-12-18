package com.zapol.android.dronemissionplanner;

import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ActionBarActivity implements MissionsTasksFragment.FragmentInterface {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private MissionCursorAdapter missionsAdapter;
    private SimpleCursorAdapter tasksAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    DbHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        dbh = new DbHelper(getApplication());
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        missionsAdapter = new MissionCursorAdapter(getApplication(), android.R.layout.simple_spinner_item, dbh.getMissions(), from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        missionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasksAdapter = new MissionCursorAdapter(getApplication(), android.R.layout.simple_spinner_item, dbh.getTasks(0), from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        tasksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddMission(String name) {
        try {
//            Mission mission = new Mission(getApplicationContext(), name);
            if(name.isEmpty())
            {
                throw new Exception(getResources().getText(R.string.missionNameEmpty).toString());
            }
            else {
                DbHelper db = new DbHelper(getApplicationContext());
                db.addMission(name);
                missionsAdapter.changeCursor(dbh.getMissions());
            }
        }
        catch(Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.couldNotCreateMission);
            builder.setMessage(e.getMessage())
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.show();
        }
    }

    @Override
    public void onRemoveMission(long missionId) {
        dbh.removeMission(missionId);
        missionsAdapter.changeCursor(dbh.getMissions());
    }

    @Override
    public long onAddTask(long missionId) {
        return 0;
    }

    @Override
    public void onRemoveTask(long taskId) {

    }

    @Override
    public CursorAdapter getMissionAdapter() {
        return this.missionsAdapter;
    }

    @Override
    public CursorAdapter getTasksAdapter() {
        return this.tasksAdapter;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            ActionBar actionBar = getSupportActionBar();
            switch(position)
            {
                case 0:
//                    actionBar.setTitle(R.string.title_MissionsTasks);
                    return MissionsTasksFragment.newInstance();
                case 1:
//                    actionBar.setTitle(R.string.title_Map);
                    return MapFragment.newInstance();
                case 2:
//                    actionBar.setTitle(R.string.title_Control);
                    return ControlFragment.newInstance();
            }
            return null;
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_MissionsTasks);
                case 1:
                    return getString(R.string.title_Map);
                case 2:
                    return getString(R.string.title_Control);
            }
            return null;
        }
    }
}
