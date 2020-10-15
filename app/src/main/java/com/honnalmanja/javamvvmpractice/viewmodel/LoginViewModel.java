package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    UserRepository userRepository;

    public LoginViewModel(){
        userRepository = new UserRepository();
    }

    public MutableLiveData<UserLiveData> getLoginSuccessLiveData() {
        return userRepository.getLoginSuccessLiveData();
    }

    public void postLoginRequest(String email, String password){
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        userRepository.postLoginRequest(loginUserRequest);
    }

    public void clear(){
        userRepository.clear();
    }
}
