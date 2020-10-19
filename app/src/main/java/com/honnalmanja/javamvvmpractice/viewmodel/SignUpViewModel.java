package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    UserRepository repository;
    MutableLiveData<UserLiveData> signUpSuccessLiveData = new MutableLiveData<>();

    public SignUpViewModel() {
        this.repository = new UserRepository();
    }

    public MutableLiveData<UserLiveData> getSignUpSuccessLiveData() {
        return repository.getSignUpResponseLiveData();
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
