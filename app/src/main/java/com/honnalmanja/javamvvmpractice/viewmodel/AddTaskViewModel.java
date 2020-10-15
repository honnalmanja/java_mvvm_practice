package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;

import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private String userID;

    public AddTaskViewModel() {
        taskRepository = new TaskRepository();
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void postTaskDescription(String description){
        taskRepository.postTask(new AddTaskRequest(description));
    }

    public void clear(){
        taskRepository.clear();
    }
}
