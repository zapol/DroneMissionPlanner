package com.zapol.android.dronemissionplanner;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.zapol.android.dronemissionplanner.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MissionsTasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
//    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private MissionCursorAdapter missionsAdapter;
    private DbHelper dbh;
    private SimpleCursorAdapter tasksAdapter;

    // TODO: Rename and change types of parameters
    public static MissionsTasksFragment newInstance() {
        MissionsTasksFragment fragment = new MissionsTasksFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MissionsTasksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        dbh = new DbHelper(getActivity().getApplication());
//        DbHelper db = new DbHelper(getActivity());
//        String[] from = new String[] {"name"};
//        int[] to = new int[] {android.R.id.text1};
//        SimpleCursorAdapter sca = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, db.getMissions(), from, to);
//        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        Spinner spin = (Spinner) view.findViewById(R.id.missions);
//        spin.setAdapter(sca);
//        missionsAdapter = new CursorAdapter(getActivity(), db.getMissions());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missionstasks, container, false);

//        // Set the adapter
//        mListView = (AbsListView) view.findViewById(R.id.tasks);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
//
//        // Set OnItemClickListener so we can be notified on item clicks
//        mListView.setOnItemClickListener(this);

        // Add Mission callback
        Button addMissionbt=(Button) view.findViewById(R.id.addMissionBtn);
        addMissionbt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onAddMission();
                        missionsAdapter.changeCursor(dbh.getMissions());
                        missionsAdapter.notifyDataSetChanged();
                    }
                });
        // Remove Mission callback
        Button removeMissionBt=(Button) view.findViewById(R.id.removeMissionBtn);
        removeMissionBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getView();
                        Spinner missionsList = (Spinner) view.findViewById(R.id.missions);
                        mListener.onRemoveMission(missionsList.getSelectedItemId());
                        missionsAdapter.changeCursor(dbh.getMissions());
                        missionsAdapter.notifyDataSetChanged();
                    }
                });
        // Add Task callback
        Button addTaskBt=(Button) view.findViewById(R.id.addTaskBtn);
        addTaskBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getView();
                        Spinner missionsList = (Spinner) view.findViewById(R.id.missions);
                        mListener.onAddTask(missionsList.getSelectedItemId());
                    }
                });
        // Remove Task callback
        Button removeTaskBt=(Button) view.findViewById(R.id.removeTaskBtn);
        removeTaskBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = getView();
                        Spinner tasksList = (Spinner) view.findViewById(R.id.tasks);
                        mListener.onRemoveTask(tasksList.getSelectedItemId());
                    }
                });

        DbHelper db = new DbHelper(getActivity().getApplication());
        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};

        missionsAdapter = new MissionCursorAdapter(getActivity().getApplication(), android.R.layout.simple_spinner_item, dbh.getMissions(), from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        missionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spin = (Spinner) view.findViewById(R.id.missions);
        spin.setAdapter(missionsAdapter);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onAddMission();
        public void onRemoveMission(long missionId);
        public long onAddTask(long missionId);
        public void onRemoveTask(long taskId);
    }

}
