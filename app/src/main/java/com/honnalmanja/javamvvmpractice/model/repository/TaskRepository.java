package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.app.Task;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TaskRepository {

    private static final String TAG = TaskRepository.class.getSimpleName();

    @Inject
    TaskManagerService taskManagerService;

    @Inject
    AppDatabase appDatabase;

    @Inject
    AppPreference appPreference;

    MutableLiveData<List<Task>> taskLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TaskRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<List<Task>> getTaskLiveData() {
        return taskLiveData;
    }

    public void clear(){
        compositeDisposable.clear();
    }
}
