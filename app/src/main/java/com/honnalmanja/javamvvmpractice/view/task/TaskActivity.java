package com.honnalmanja.javamvvmpractice.view.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.viewmodel.TaskViewModel;

public class TaskActivity extends AppCompatActivity {

    private TaskViewModel viewModel;
    private TaskActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        activity = this;

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        viewModel.askUserID();
        viewModel.getUserID().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String userID) {
                if(userID != null && userID.isEmpty()){
                    startActivity(new Intent(activity, LoginActivity.class));
                } else {
                    Toast.makeText(activity, userID, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}