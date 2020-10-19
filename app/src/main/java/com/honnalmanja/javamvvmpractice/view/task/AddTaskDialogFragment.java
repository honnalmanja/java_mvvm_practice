package com.honnalmanja.javamvvmpractice.view.task;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
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
import com.honnalmanja.javamvvmpractice.model.app.TaskLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.utils.CommonUtils;
import com.honnalmanja.javamvvmpractice.viewmodel.TaskViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.Objects;
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

    private TaskViewModel viewModel;

    public AddTaskDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param newTask send true if new task.
     * @param position position of task present for edit send -1 for new task.
     * @param taskID server ID of task that need to be updated
     *
     * @return A new instance of fragment AddTaskDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskDialogFragment newInstance(boolean newTask, String taskID, int position) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(CommonUtils.IS_NEW_TASK_KEY, newTask);
        args.putString(CommonUtils.TASK_ID_KEY, taskID);
        args.putInt(CommonUtils.TASK_POSITION_KEY, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
                                if(getArguments() != null){
                                    if(getArguments().getBoolean(CommonUtils.IS_NEW_TASK_KEY)){
                                        postTask();
                                    } else {
                                        int position = getArguments().getInt(CommonUtils.TASK_POSITION_KEY);
                                        updateTheCurrentTask(position);
                                    }
                                } else {
                                    postTask();
                                }

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
                                if(getArguments() != null) {
                                    if (getArguments().getBoolean(CommonUtils.IS_NEW_TASK_KEY)) {
                                        int position = getArguments().getInt(CommonUtils.TASK_POSITION_KEY);
                                        updateTaskToList(viewModel.getTask(), position, "Update Canceled");
                                    } else {
                                        dismiss();
                                    }
                                } else {
                                    dismiss();
                                }


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {

                            }
                        })
        );


        viewModel.subscribeAddTaskLiveData().observe(this, new Observer<TaskLiveData>() {
            @Override
            public void onChanged(TaskLiveData response) {

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

        viewModel.postTaskDescription(taskDescription, false);

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

        etAddTask = view.findViewById(R.id.add_task_et);

        AppCompatTextView tvTitle = view.findViewById(R.id.tool_bar_title_tv);
        if(getArguments() != null){
            if(!getArguments().getBoolean(CommonUtils.IS_NEW_TASK_KEY, true)){
                tvTitle.setText(getResources().getString(R.string.update_task_title));
                getTaskDescription(etAddTask, getArguments().getString(CommonUtils.TASK_ID_KEY, ""));
            }
        }
        tvTitle.setText(getResources().getString(R.string.add_task_title));

    }

    private void getTaskDescription(AppCompatEditText etAddTask, String taskID) {

        viewModel.getSingleTask(taskID);
        viewModel.subscribeSingleTaskLiveData().observe(this, new Observer<TaskLiveData>() {
            @Override
            public void onChanged(TaskLiveData taskLiveData) {
                if(taskLiveData.getStatusCode() == 202){
                    viewModel.setTask(taskLiveData.getTask());
                    etAddTask.setText(taskLiveData.getTask().getTaskDescription());
                } else {
                    Toast.makeText(getActivity(), taskLiveData.getMessage(), Toast.LENGTH_LONG).show();
                    dismiss();
                }
            }
        });

    }

    private void updateTheCurrentTask(int position) {

        Task task = viewModel.getTask();
        String previousDescription = task.getTaskDescription();
        String taskDescription = Objects.requireNonNull(etAddTask.getText()).toString();
        if(taskDescription.trim().isEmpty()){
            etAddTask.setError(
                    Objects.requireNonNull(getActivity())
                            .getResources().getString(R.string.enter_task_description_error)
            );
            return;
        }
        task.setTaskDescription(etAddTask.getText().toString());
        viewModel.updateTask(task);
        viewModel.subscribeUpdateTaskLiveData()
                .observe(this, new Observer<TaskLiveData>() {
                    @Override
                    public void onChanged(TaskLiveData taskLiveData) {
                        if(!(taskLiveData.getStatusCode() == (200|202|201))){
                            task.setTaskDescription(previousDescription);
                        }
                        updateTaskToList(task, position,taskLiveData.getMessage());

                    }
                });

    }

    private void updateTaskToList(Task task, int position, String message){
        ((TaskActivity) Objects.requireNonNull(getActivity()))
                .updateEditedTask(task, position);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        dismiss();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.clear();
        compositeDisposable.clear();
    }
}