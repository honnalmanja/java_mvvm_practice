package com.honnalmanja.javamvvmpractice.viewmodel;

import android.app.Application;

import com.honnalmanja.javamvvmpractice.model.repository.AppRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.core.Single;

public class TaskViewModel extends ViewModel {

    AppRepository appRepository;
    MutableLiveData<String> userID = new MutableLiveData<>();

    public TaskViewModel() {
        appRepository = new AppRepository();
    }

    public void askUserID(){
        appRepository.askUserID();
    }

    public MutableLiveData<String> getUserID(){
        return appRepository.getUserIDLiveData();
    }
}
