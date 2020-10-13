package com.honnalmanja.javamvvmpractice.model.repository;

import android.app.Application;
import android.util.Log;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.di.AppComponent;
import com.honnalmanja.javamvvmpractice.model.app.Task;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();

    @Inject
    TaskManagerService taskManagerService;

    @Inject
    AppDatabase appDatabase;

    @Inject
    AppPreference appPreference;

    Application application;
    MutableLiveData<List<Task>> taskLiveData = new MutableLiveData<>();
    MutableLiveData<String> userIDLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AppRepository(){

        TaskManager.getApp().getAppComponent().inject(this);

        Log.i(TAG, "AppRepository constructor");
        if(appPreference == null){
            Log.e(TAG, "AppPreference null");
        }
        if(appDatabase == null){
            Log.e(TAG, "AppDatabase null");
        }
        if(taskManagerService == null){
            Log.e(TAG, "TaskManagerService null");
        }
    }

    public void askUserID(){
        compositeDisposable.add(
                Single.just(appPreference.gerUserID())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(@NonNull String s) {
                                userIDLiveData.postValue(s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        })
        );

    }

    public MutableLiveData<List<Task>> getTaskLiveData() {
        return taskLiveData;
    }

    public MutableLiveData<String> getUserIDLiveData() {
        return userIDLiveData;
    }

    public void clear(){
        compositeDisposable.clear();
    }

}
