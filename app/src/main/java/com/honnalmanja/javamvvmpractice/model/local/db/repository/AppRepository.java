package com.honnalmanja.javamvvmpractice.model.local.db.repository;

import com.honnalmanja.javamvvmpractice.model.app.Task;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class AppRepository {

    @Inject
    TaskManagerService taskManagerService;

    @Inject
    AppDatabase appDatabase;

    MutableLiveData<List<Task>> taskLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();



    public void clear(){
        compositeDisposable.clear();
    }

}
