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
                                appPreference.saveUserToken(userResponse.getToken());
                                appPreference.saveUserDetails(userResponse.getUser());
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
                        loginResponseLiveData.postValue(loginResponse);
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
                        signUpResponseLiveData.postValue(signUpResponse);
                    }
                })
        );
    }

    public void postLogoutRequest(){
        compositeDisposable.add(
                taskManagerService.logoutUser("Bearer " + appPreference.gerUserToken())
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
                                                null,
                                                null
                                        );
                                        appPreference.saveUserToken("");
                                        appPreference.saveUserDetails(null);
                                    } else {
                                        userLiveData = new UserLiveData(
                                                response.code(),
                                                response.message(),
                                                null,
                                                null
                                        );
                                    }
                                    logoutResponseLiveData.postValue(userLiveData);
                                } else {
                                    userLiveData = new UserLiveData(
                                            response.code(),
                                            response.message(),
                                            null,
                                            null
                                    );
                                }
                                logoutResponseLiveData.postValue(userLiveData);
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

    public void clear(){
        compositeDisposable.clear();
    }
}
