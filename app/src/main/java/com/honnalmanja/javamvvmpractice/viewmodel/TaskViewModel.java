package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskViewModel() {
        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
    }

    public void askForAllTask(){
        taskRepository.askForAllTask();
    }

    public MutableLiveData<TasksResponse> getAllTaskLiveData() {
        return taskRepository.getAllTaskLiveData();
    }

    public void postLogout(){
        userRepository.postLogoutRequest();
    }

    public MutableLiveData<UserLiveData> subscribeLogoutResponse(){
        return userRepository.getLogoutResponseLiveData();
    }

    public void clear(){
        userRepository.clear();
        taskRepository.clear();
    }

}
