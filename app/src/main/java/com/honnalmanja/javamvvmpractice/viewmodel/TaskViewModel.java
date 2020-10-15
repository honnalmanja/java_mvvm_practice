package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private UserRepository userRepository;
    private String userID;

    public TaskViewModel() {
        userRepository = new UserRepository();
    }

    public void askUserID(){
        userRepository.askUserID();
    }

    public MutableLiveData<String> watchUserID() {
        return userRepository.getUserIDLiveData();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
