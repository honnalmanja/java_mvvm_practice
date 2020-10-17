package com.honnalmanja.javamvvmpractice.view.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;
import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.viewmodel.TaskViewModel;
import com.jakewharton.rxbinding4.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

public class TaskActivity extends AppCompatActivity implements TaskClickListener {

    private static final String TAG = TaskActivity.class.getSimpleName();

    private TaskViewModel viewModel;
    private TaskActivity activity;
    private FloatingActionButton addTaskFab;
    private ConstraintLayout constraintLayout;

    private RecyclerView rvTasks;
    private TaskListAdapter taskListAdapter;
    private AppCompatImageView ibProfile, ibLogout;

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

        compositeDisposable.add(
                RxView.clicks(ibLogout).throttleFirst(2, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Unit>() {
                            @Override
                            public void accept(Unit unit) throws Throwable {
                                Toast.makeText(activity, "Logout", Toast.LENGTH_LONG).show();
                                callLogOutService();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                LogUtil.e(TAG, "throwable: " + throwable);
                            }
                        })
        );

        attachProfilePhoto();

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchForTasks();
    }

    public void bindView(){

        constraintLayout = findViewById(R.id.task_list_llc);

        ibProfile = findViewById(R.id.tool_bar_back_btn);
        ibProfile.setVisibility(View.VISIBLE);
        ibLogout = findViewById(R.id.tool_bar_more_options_btn);
        ibLogout.setVisibility(View.VISIBLE);
        ibLogout.setImageDrawable(ResourcesCompat.getDrawable(
                getResources(), R.drawable.ic_baseline_power_settings_new_24, null
        ));

        addTaskFab = findViewById(R.id.add_task_fab);

        rvTasks = findViewById(R.id.task_list_rv);
        rvTasks.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        taskListAdapter = new TaskListAdapter(new ArrayList<>(),this);
        rvTasks.setAdapter(taskListAdapter);

    }

    @Override
    public void onTaskClicked(Task task, int position) {
        Snackbar.make(constraintLayout, task.getTaskID(), BaseTransientBottomBar.LENGTH_LONG).show();
    }


    public void searchForTasks(){

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
                                taskListAdapter.setTaskList(response.getTaskList());
                            }
                        } else if(response.getStatusCode() == 401) {
                            goToLoginPage();
                            Toast.makeText(activity, response.getMessage(), Toast.LENGTH_LONG).show();
                        }else if(response.getStatusCode() == 404) {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void goToLoginPage() {
        startActivity(new Intent(activity, LoginActivity.class));
    }

    private void callLogOutService() {

        viewModel.postLogout();
        viewModel.subscribeLogoutResponse()
                .observe(this, new Observer<UserLiveData>() {
                    @Override
                    public void onChanged(UserLiveData response) {
                        LogUtil.d(TAG, "Logout: " + response);
                        if(response.getStatusCode() == 200 || response.getStatusCode() == 401) {
                            goToLoginPage();
                            Toast.makeText(activity, response.getMessage(), Toast.LENGTH_LONG).show();
                        }else if(response.getStatusCode() == 404) {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(constraintLayout, response.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void attachProfilePhoto() {

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(120)  // width in px
                .height(120)
                .fontSize(60) /* size in px */
                .bold()// height in px
                .withBorder(4)
                .endConfig()
                .buildRoundRect("A",
                        getResources().getColor(R.color.colorAccent),
                        100
                );
        ibProfile.setImageDrawable(drawable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        viewModel.clear();
    }
}