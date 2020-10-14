package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    UserRepository userRepository;
    MutableLiveData<String> userID = new MutableLiveData<>();

    public TaskViewModel() {
        userRepository = new UserRepository();
    }

    public void askUserID(){
        userRepository.askUserID();
    }

    public MutableLiveData<String> getUserID(){
        return userRepository.getUserIDLiveData();
    }
}
