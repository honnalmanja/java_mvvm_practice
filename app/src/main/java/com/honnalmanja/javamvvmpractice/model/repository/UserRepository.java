package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.User;
import com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse;
import com.honnalmanja.javamvvmpractice.utils.CommonUtils;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
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

    private UserLiveData loginResponse, signUpResponse;

    private MutableLiveData<UserLiveData> loginResponseLiveData = new MutableLiveData<>();
    MutableLiveData<UserLiveData> signUpResponseLiveData = new MutableLiveData<>();
    MutableLiveData<UserLiveData> logoutResponseLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public UserRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<UserLiveData> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }

    public MutableLiveData<UserLiveData> getSignUpResponseLiveData() {
        return signUpResponseLiveData;
    }

    public MutableLiveData<UserLiveData> getLogoutResponseLiveData() {
        return logoutResponseLiveData;
    }

    public void postLoginRequest(LoginUserRequest loginUserRequest){
        compositeDisposable.add(
                taskManagerService.loginUser(loginUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<UserResponse>>() {
                    @Override
                    public void onNext(@NonNull Response<UserResponse> response) {
                        if(response.code() == 202){
                            UserResponse userResponse = response.body();
                            if(userResponse != null){
                                loginResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        userResponse.getUser(),
                                        null
                                );
                                loginResponse.setToken(userResponse.getToken());
                            } else {
                                loginResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        null,
                                        null
                                );
                            }
                        } else {
                            loginResponse = new UserLiveData(
                                    response.code(),
                                    response.message(),
                                    null,
                                    null
                            );
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"ServerResponse: " + e);
                        LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                        loginResponseLiveData.postValue(new UserLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }

                    @Override
                    public void onComplete() {
                        if(loginResponse.getStatusCode() == 202){
                            saveUserToPreference(CommonUtils.LOGIN_API, loginResponse);
                        } else {
                            loginResponseLiveData.postValue(loginResponse);
                        }

                    }
                })
        );
    }

    private void saveUserToPreference(String api, UserLiveData userLiveData) {
        compositeDisposable.add(
                appPreference.saveUserAndToken(userLiveData.getUser(),
                        userLiveData.getToken()
                ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        if(api.equalsIgnoreCase(CommonUtils.LOGIN_API)){
                            loginResponseLiveData.postValue(userLiveData);
                        } else if(api.equalsIgnoreCase(CommonUtils.SIGN_UP_API)){
                            signUpResponseLiveData.postValue(userLiveData);
                        } else {
                            logoutResponseLiveData.postValue(userLiveData);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                })
        );
    }

    public void postSignUpRequest(CreateUserRequest createUserRequest){

        compositeDisposable.add(
            taskManagerService.createUser(createUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<UserResponse>>() {
                    @Override
                    public void onNext(@NonNull Response<UserResponse> response) {
                        if(response.code() == 200){
                            com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse userResponse = response.body();
                            if(userResponse != null){
                                signUpResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        userResponse.getUser(),
                                        null
                                );
                            } else {
                                signUpResponse = new UserLiveData(
                                        response.code(),
                                        response.message(),
                                        null,
                                        null
                                );
                            }
                        } else {
                            signUpResponse = new UserLiveData(
                                    response.code(),
                                    response.message(),
                                    null,
                                    null
                            );
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG,"ServerResponse: " + e);
                        LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                        loginResponseLiveData.postValue(new UserLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }

                    @Override
                    public void onComplete() {
                        if(signUpResponse.getStatusCode() == 200){
                            saveUserToPreference(CommonUtils.SIGN_UP_API, signUpResponse);
                        } else {
                            signUpResponseLiveData.postValue(signUpResponse);
                        }
                    }
                })
        );
    }

    public void postLogoutRequest(){
        compositeDisposable.add(
                taskManagerService.logoutUser("Bearer " + appPreference.gerUserToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Response<UserResponse>>() {
                            @Override
                            public void onSuccess(Response<UserResponse> response) {
                                LogUtil.d(TAG,"ServerResponse: " + response);
                                UserLiveData userLiveData;
                                if(response.code() == 202){
                                    UserResponse userResponse = response.body();
                                    userLiveData = new UserLiveData(
                                            response.code(),
                                            response.message(),
                                            null,
                                            null
                                    );
                                    saveUserToPreference(CommonUtils.LOGOUT_API, userLiveData);

                                } else {
                                    userLiveData = new UserLiveData(
                                            response.code(),
                                            response.message(),
                                            null,
                                            null
                                    );
                                    logoutResponseLiveData.postValue(userLiveData);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                LogUtil.e(TAG,"ServerResponse: " + e);
                                LogUtil.e(TAG,"ServerResponse: " + e.getMessage());
                                logoutResponseLiveData.postValue(new UserLiveData(
                                        500,
                                        e.getMessage(),
                                        null,
                                        null
                                ));
                            }
                        })
        );
    }

    public Single<User> getSavedUser(){
        return appPreference.getUserDetails();
    }

    public void clear(){
        compositeDisposable.clear();
    }
}
