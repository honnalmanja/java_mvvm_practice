package com.honnalmanja.javamvvmpractice.model.repository;

import android.util.Log;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.app.ServerResponse;
import com.honnalmanja.javamvvmpractice.model.app.Task;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.TasksResponse;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class TaskRepository {

    private static final String TAG = TaskRepository.class.getSimpleName();

    @Inject
    TaskManagerService taskManagerService;

    @Inject
    AppDatabase appDatabase;

    @Inject
    AppPreference appPreference;

    private MutableLiveData<List<Task>> allTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<ServerResponse> addTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<ServerResponse> updateTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<ServerResponse> deleteTaskLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TaskRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<List<Task>> getAllTaskLiveData() {
        return allTaskLiveData;
    }

    public MutableLiveData<ServerResponse> getAddTaskLiveData() {
        return addTaskLiveData;
    }

    public MutableLiveData<ServerResponse> getUpdateTaskLiveData() {
        return updateTaskLiveData;
    }

    public MutableLiveData<ServerResponse> getDeleteTaskLiveData() {
        return deleteTaskLiveData;
    }

    public void postTask(AddTaskRequest addTaskRequest){
        compositeDisposable.add(
            taskManagerService.addTask(addTaskRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<Response<TasksResponse>>() {
                @Override
                public void onSuccess(@NonNull Response<TasksResponse> response) {
                    LogUtil.i(TAG, "Response: " + response);
                    ServerResponse addTaskResponse;
                    if(response.code() == 200){
                        addTaskResponse = new ServerResponse(true, "Task Added");
                    } else if(response.code() == 404){
                        addTaskResponse = new ServerResponse(false, "Task not added");
                    } else {
                        addTaskResponse = new ServerResponse(false, "Server Not responsive");
                    }

                    addTaskLiveData.postValue(addTaskResponse);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogUtil.e(TAG, "Throwable: "+e);
                    LogUtil.e(TAG, "Message: "+e.getMessage());
                    addTaskLiveData.postValue(new ServerResponse(false, e.getMessage()));
                }
            })
        );
    }

    public void getAllTask(){

    }

    public void updateTask(AddTaskRequest addTaskRequest){

    }

    public void deleteTask(){

    }

    public void clear(){
        compositeDisposable.clear();
    }
}
