package com.honnalmanja.javamvvmpractice.view.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.viewmodel.TaskViewModel;

public class TaskActivity extends AppCompatActivity {

    private TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
    }
}