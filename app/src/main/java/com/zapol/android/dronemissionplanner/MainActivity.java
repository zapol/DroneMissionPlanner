package com.zapol.android.dronemissionplanner;

import java.util.Locale;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements MissionsTasksFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, ControlFragment.OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        getSupportActionBar().setTitle(mSectionsPagerAdapter.getPageTitle(0));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override public void onPageScrollStateChanged(    int arg0){
            }
            @Override public void onPageScrolled(    int arg0,    float arg1,    int arg2){
            }
            @Override public void onPageSelected(    int position){
                getSupportActionBar().setTitle(mSectionsPagerAdapter.getPageTitle(position));
            }
        }
        );
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

    public void onFragmentInteraction(Uri uri)
    {

    }

    public void onFragmentInteraction(String id)
    {

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
            ActionBar actionBar = getSupportActionBar();
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

//        @Override
        public void onPageSelected(int position)
        {
            ActionBar actionBar = getSupportActionBar();
            switch(position)
            {
                case 0:
                    actionBar.setTitle(R.string.title_MissionsTasks);
                    break;
                case 1:
                    actionBar.setTitle(R.string.title_Map);
                    break;
                case 2:
                    actionBar.setTitle(R.string.title_Control);
                    break;
            }
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
