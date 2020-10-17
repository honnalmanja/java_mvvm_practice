package com.honnalmanja.javamvvmpractice.view.task;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.viewmodel.AddTaskViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link DialogFragment} subclass.
 * Use the {@link AddTaskDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskDialogFragment extends DialogFragment {

    private AppCompatImageView ibCancel, ibAccept;
    private AppCompatEditText etAddTask;
    private LinearLayoutCompat llcHolder;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AddTaskViewModel viewModel;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddTaskDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance() {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddTaskViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            Window window = getDialog().getWindow();
            if(window != null){
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        compositeDisposable.add(
                RxView.clicks(ibAccept).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                postTask();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        })
        );

        compositeDisposable.add(
                RxView.clicks(ibCancel).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                dismiss();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        })
        );


        viewModel.watchAddTaskLiveData().observe(this, new Observer<TasksResponse>() {
            @Override
            public void onChanged(TasksResponse response) {

                if(response.getStatusCode() == 201){
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
                    dismiss();
                } else if(response.getStatusCode() == 401) {
                    dismiss();
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Snackbar.make(llcHolder, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                }

            }
        });

    }

    public void postTask(){

        String taskDescription = etAddTask.getText().toString();

        if(taskDescription.trim().isEmpty()){
            etAddTask.setError("Task description cannot be null");
            return;
        }

        viewModel.postTaskDescription(taskDescription);

    }

    private void bindViews(View view) {

        llcHolder = view.findViewById(R.id.add_task_holder_llc);

        ibCancel = view.findViewById(R.id.tool_bar_back_btn);
        ibCancel.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_close_24, null)
        );
        ibCancel.setVisibility(View.VISIBLE);

        ibAccept = view.findViewById(R.id.tool_bar_more_options_btn);
        ibAccept.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_baseline_check_24, null)
        );
        ibAccept.setVisibility(View.VISIBLE);

        AppCompatTextView tvTitle = view.findViewById(R.id.tool_bar_title_tv);
        tvTitle.setText(getResources().getString(R.string.add_task_title));

        etAddTask = view.findViewById(R.id.add_task_et);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.clear();
        compositeDisposable.clear();
    }
}