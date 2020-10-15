package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private UserRepository userRepository;
    private String userToken;

    public TaskViewModel() {
        userRepository = new UserRepository();
    }

    public void askUserToken(){
        userRepository.askUserToken();
    }

    public MutableLiveData<String> watchUserID() {
        return userRepository.getUserIDLiveData();
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
