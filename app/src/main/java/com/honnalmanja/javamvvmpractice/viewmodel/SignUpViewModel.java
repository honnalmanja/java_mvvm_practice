package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.ServerResponse;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    UserRepository repository;
    MutableLiveData<ServerResponse> signUpSuccessLiveData = new MutableLiveData<>();

    public SignUpViewModel() {
        this.repository = new UserRepository();
    }

    public MutableLiveData<ServerResponse> getSignUpSuccessLiveData() {
        return repository.getSignUpSuccessLiveData();
    }

    public void postSignUpData(String email, String password, String name, int age){

        CreateUserRequest createUserRequest
                = new CreateUserRequest(email, password, name, age);

        repository.postSignUpRequest(createUserRequest);

    }

    public void clear(){
        repository.clear();
    }
}
