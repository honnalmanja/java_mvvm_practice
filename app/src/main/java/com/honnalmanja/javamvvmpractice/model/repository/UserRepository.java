package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.app.ServerResponse;
import com.honnalmanja.javamvvmpractice.model.remote.users.User;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UserRepository {

    private static final String TAG = TaskRepository.class.getSimpleName();

    @Inject
    TaskManagerService taskManagerService;

    @Inject
    AppDatabase appDatabase;

    @Inject
    AppPreference appPreference;

    MutableLiveData<String> userIDLiveData = new MutableLiveData<>();
    MutableLiveData<ServerResponse> loginSuccessLiveData = new MutableLiveData<>();
    MutableLiveData<ServerResponse> signUpSuccessLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UserRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<String> getUserIDLiveData() {
        return userIDLiveData;
    }

    public MutableLiveData<ServerResponse> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }

    public MutableLiveData<ServerResponse> getSignUpSuccessLiveData() {
        return signUpSuccessLiveData;
    }

    public void askUserToken(){
        compositeDisposable.add(
                Single.just(appPreference.gerUserToken())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(@NonNull String s) {
                               userIDLiveData.postValue(s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                userIDLiveData.postValue("");
                            }
                        })
        );
    }

    public void postLoginRequest(LoginUserRequest loginUserRequest){
        compositeDisposable.add(
                taskManagerService.loginUser(loginUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<UserResponse>>() {
                    @Override
                    public void onSuccess(Response<UserResponse> response) {
                        LogUtil.d(TAG,"UserResponse: " + response);
                        ServerResponse loginResponse;
                        if(response.code() == 202){
                            loginResponse = new ServerResponse(true, response.message());
                            UserResponse userResponse = response.body();
                            if(userResponse != null){
                                appPreference.saveUserToken(userResponse.getToken());
                                appPreference.saveUserDetails(userResponse.getUser());
                            }
                        } else if(response.code() == 404) {
                            loginResponse = new ServerResponse(false, response.message());
                        } else {
                            loginResponse = new ServerResponse(false, response.message());
                        }
                        loginSuccessLiveData.postValue(loginResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"UserResponse: " + e);
                        LogUtil.e(TAG,"UserResponse: " + e.getMessage());
                        loginSuccessLiveData.postValue(new ServerResponse(false, e.getMessage()));
                    }
                })
        );
    }

    public void postSignUpRequest(CreateUserRequest createUserRequest){

        compositeDisposable.add(
            taskManagerService.createUser(createUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<UserResponse>>() {
                    @Override
                    public void onSuccess(@NonNull Response<UserResponse> response) {
                        ServerResponse signUpResponse;
                        if(response.code() == 200){
                            signUpResponse = new ServerResponse(true, response.message());
                            UserResponse userResponse = response.body();
                            if(userResponse != null){
                                appPreference.saveUserToken(userResponse.getToken());
                                appPreference.saveUserDetails(userResponse.getUser());
                            }
                        } else if(response.code() == 404) {
                            signUpResponse = new ServerResponse(false, response.message());
                        } else {
                            signUpResponse = new ServerResponse(false, response.message());
                        }
                        signUpSuccessLiveData.postValue(signUpResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"ServerResponse: " + e);
                        LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                        loginSuccessLiveData.postValue(new ServerResponse(false, e.getMessage()));
                    }
                })
        );

    }

    public void clear(){
        compositeDisposable.clear();
    }
}
