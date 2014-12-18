package com.zapol.android.dronemissionplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.zapol.android.dronemissionplanner.MissionsTasksFragment.FragmentInterface}
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

    private FragmentInterface fInterface;

    /**
     * The fragment's ListView/GridView.
     */
//    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private CursorAdapter missionsAdapter,tasksAdapter;

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
//        dbh = new DbHelper(getActivity().getApplication());
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

        this.missionsAdapter = fInterface.getMissionAdapter();
        this.tasksAdapter = fInterface.getTasksAdapter();

        // Add Mission callback
        Button addMissionbt=(Button) view.findViewById(R.id.addMissionBtn);
        addMissionbt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                        alert.setMessage(R.string.missionName);

                        // Set an EditText view to get user input
                        final EditText input = new EditText(getActivity());

                        alert.setView(input);
                        alert.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String name = input.getText().toString();
                                fInterface.onAddMission(name);
                            }
                        });

                        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                        alert.show();
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
                        fInterface.onRemoveMission(missionsList.getSelectedItemId());
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
                        fInterface.onAddTask(missionsList.getSelectedItemId());
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
                        fInterface.onRemoveTask(tasksList.getSelectedItemId());
                    }
                });

        Spinner missionSpin = (Spinner) view.findViewById(R.id.missions);
        missionSpin.setAdapter(missionsAdapter);
        Spinner tasksSpin = (Spinner) view.findViewById(R.id.tasks);
        tasksSpin.setAdapter(tasksAdapter);
//        spin.setAdapter(tasksAdapter);


        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fInterface = (FragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fInterface = null;
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
    public interface FragmentInterface {
        public void onAddMission(String name);
        public void onRemoveMission(long missionId);
        public long onAddTask(long missionId);
        public void onRemoveTask(long taskId);
        public CursorAdapter getMissionAdapter();
        public CursorAdapter getTasksAdapter();
    }

}
