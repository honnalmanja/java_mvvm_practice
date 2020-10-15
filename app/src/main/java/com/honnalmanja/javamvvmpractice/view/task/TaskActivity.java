package com.honnalmanja.javamvvmpractice.view.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;
import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.viewmodel.TaskViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.concurrent.TimeUnit;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = TaskActivity.class.getSimpleName();

    private TaskViewModel viewModel;
    private TaskActivity activity;
    private FloatingActionButton addTaskFab;
    private ConstraintLayout constraintLayout;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        activity = this;

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        bindView();

        compositeDisposable.add(
                RxView.clicks(addTaskFab).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                FragmentManager fragmentManager
                                        = getSupportFragmentManager();
                                AddTaskDialogFragment addTaskDialogFragment
                                        = AddTaskDialogFragment.newInstance();
                                addTaskDialogFragment.show(fragmentManager, TAG);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                LogUtil.e(TAG, "throwable: " + throwable);
                            }
                        })
        );

    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.askForAllTask();
        viewModel.getAllTaskLiveData()
                .observe(this, new Observer<TasksResponse>() {
                    @Override
                    public void onChanged(TasksResponse response) {
                        LogUtil.d(TAG, "TasksResponse: "+response);
                        if(response.getStatusCode() == 200){
                            if(response.getTaskList() == null || response.getTaskList().size() == 0){
                                Snackbar.make(constraintLayout, "No Task Found", BaseTransientBottomBar.LENGTH_LONG).show();
                            } else {

                            }
                        } else if(response.getStatusCode() == 401) {
                            startActivity(new Intent(activity, LoginActivity.class));
                            Toast.makeText(activity, response.getMessage(), Toast.LENGTH_LONG).show();
                        }else if(response.getStatusCode() == 404) {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void bindView(){

        constraintLayout = findViewById(R.id.task_list_llc);
        addTaskFab = findViewById(R.id.add_task_fab);

    }
}