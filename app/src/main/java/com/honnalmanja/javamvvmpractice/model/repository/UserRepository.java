package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
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
import io.reactivex.rxjava3.disposables.CompositeDisposable;
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


    MutableLiveData<UserLiveData> loginSuccessLiveData = new MutableLiveData<>();
    MutableLiveData<UserLiveData> signUpSuccessLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UserRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<UserLiveData> getLoginSuccessLiveData() {
        return loginSuccessLiveData;
    }

    public MutableLiveData<UserLiveData> getSignUpSuccessLiveData() {
        return signUpSuccessLiveData;
    }

    public void postLoginRequest(LoginUserRequest loginUserRequest){
        compositeDisposable.add(
                taskManagerService.loginUser(loginUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse>>() {
                    @Override
                    public void onSuccess(Response<com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse> response) {
                        LogUtil.d(TAG,"ServerResponse: " + response);
                        UserLiveData userLiveData;
                        if(response.code() == 202){
                            UserResponse userResponse = response.body();
                            if(userResponse != null){
                                userLiveData = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        userResponse.getUser(),
                                        null
                                );
                                appPreference.saveUserToken(userResponse.getToken());
                                appPreference.saveUserDetails(userResponse.getUser());
                            } else {
                                userLiveData = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        null,
                                        null
                                );
                            }
                            loginSuccessLiveData.postValue(userLiveData);
                        } else {
                            userLiveData = new UserLiveData(
                                    response.code(),
                                    response.message(),
                                    null,
                                    null
                            );
                        }
                        loginSuccessLiveData.postValue(userLiveData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"ServerResponse: " + e);
                        LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                        loginSuccessLiveData.postValue(new UserLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }
                })
        );
    }

    public void postSignUpRequest(CreateUserRequest createUserRequest){

        compositeDisposable.add(
            taskManagerService.createUser(createUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse>>() {
                    @Override
                    public void onSuccess(@NonNull Response<com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse> response) {
                        UserLiveData signUpResponse;
                        if(response.code() == 200){
                            com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse userResponse = response.body();
                            if(userResponse != null){
                                signUpResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        userResponse.getUser(),
                                        null
                                );
                                appPreference.saveUserToken(userResponse.getToken());
                                appPreference.saveUserDetails(userResponse.getUser());
                            } else {
                                signUpResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        null,
                                        null
                                );
                            }
                            signUpSuccessLiveData.postValue(signUpResponse);
                        } else {
                            signUpResponse = new UserLiveData(
                                    response.code(),
                                    response.message(),
                                    null,
                                    null
                            );
                        }
                        signUpSuccessLiveData.postValue(signUpResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"ServerResponse: " + e);
                        LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                        loginSuccessLiveData.postValue(new UserLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }
                })
        );

    }

    public void clear(){
        compositeDisposable.clear();
    }
}
