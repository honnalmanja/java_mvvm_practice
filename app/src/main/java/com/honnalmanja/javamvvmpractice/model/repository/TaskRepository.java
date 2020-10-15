package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
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

    private MutableLiveData<TasksResponse> allTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TasksResponse> addTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TasksResponse> updateTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TasksResponse> deleteTaskLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TaskRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<TasksResponse> getAllTaskLiveData() {
        return allTaskLiveData;
    }

    public MutableLiveData<TasksResponse> getAddTaskLiveData() {
        return addTaskLiveData;
    }

    public MutableLiveData<TasksResponse> getUpdateTaskLiveData() {
        return updateTaskLiveData;
    }

    public MutableLiveData<TasksResponse> getDeleteTaskLiveData() {
        return deleteTaskLiveData;
    }

    public void askForAllTask(){
        compositeDisposable.add(
                taskManagerService.getAllTasks("Bearer "+appPreference.gerUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<List<Task>>>() {
                    @Override
                    public void onNext(@NonNull Response<List<Task>> response) {
                        LogUtil.d(TAG, "AllTaskResponse: " + response);
                        allTaskLiveData.postValue(new TasksResponse(
                                response.code(),
                                response.message(),
                                null,
                                response.body()
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG, "Throwable: "+e);
                        LogUtil.e(TAG, "Message: "+e.getMessage());
                        allTaskLiveData.postValue(new TasksResponse(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void postTask(AddTaskRequest addTaskRequest){
        compositeDisposable.add(
            taskManagerService.addTask(appPreference.gerUserToken(),addTaskRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<Response<Task>>() {
                @Override
                public void onSuccess(@NonNull Response<Task> response) {
                    LogUtil.i(TAG, "Response: " + response);

                    addTaskLiveData.postValue(new TasksResponse(
                            response.code(),
                            response.message(),
                            response.body(),
                            null
                    ));
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogUtil.e(TAG, "Throwable: "+e);
                    LogUtil.e(TAG, "Message: "+e.getMessage());
                    addTaskLiveData.postValue(new TasksResponse(
                            500,
                            e.getMessage(),
                            null,
                            null
                    ));
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
