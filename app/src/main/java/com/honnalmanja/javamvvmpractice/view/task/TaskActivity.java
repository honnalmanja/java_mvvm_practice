package com.honnalmanja.javamvvmpractice.view.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.honnalmanja.javamvvmpractice.R;
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
                                        = AddTaskDialogFragment.newInstance(viewModel.getUserToken());
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

        viewModel.askUserToken();
        viewModel.watchUserID().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String userToken) {
                if(userToken != null && userToken.isEmpty()){
                    startActivity(new Intent(activity, LoginActivity.class));
                } else {
                    viewModel.setUserToken(userToken);
                }
            }
        });
    }

    public void bindView(){

        addTaskFab = findViewById(R.id.add_task_fab);

    }
}