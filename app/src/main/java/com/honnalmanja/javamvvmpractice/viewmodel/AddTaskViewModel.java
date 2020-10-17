package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private TaskRepository taskRepository;

    public AddTaskViewModel() {
        taskRepository = new TaskRepository();
    }

    public void postTaskDescription(String description){
        taskRepository.postTask(new AddTaskRequest(description));
    }

    public MutableLiveData<TasksResponse> watchAddTaskLiveData() {
        return taskRepository.getAddTaskLiveData();
    }

    public void clear(){
        taskRepository.clear();
    }
}
