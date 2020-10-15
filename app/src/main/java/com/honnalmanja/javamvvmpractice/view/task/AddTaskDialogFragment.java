package com.honnalmanja.javamvvmpractice.view.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honnalmanja.javamvvmpractice.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskDialogFragment extends Fragment {

    private static final String USER_ID = "user_id";

    private String userID;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userID String userID to create task with user loggedIN.
     * @return A new instance of fragment AddTaskDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(String userID) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, container, false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

    }
}