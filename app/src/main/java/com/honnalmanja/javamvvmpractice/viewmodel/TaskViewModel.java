package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;

    public TaskViewModel() {
        taskRepository = new TaskRepository();
    }

    public void askForAllTask(){
        taskRepository.askForAllTask();
    }

    public MutableLiveData<TasksResponse> getAllTaskLiveData() {
        return taskRepository.getAllTaskLiveData();
    }

}
