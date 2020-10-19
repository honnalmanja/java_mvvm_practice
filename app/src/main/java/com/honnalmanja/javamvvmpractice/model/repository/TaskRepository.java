package com.honnalmanja.javamvvmpractice.model.repository;

import com.honnalmanja.javamvvmpractice.TaskManager;
import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;
import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;
import com.honnalmanja.javamvvmpractice.model.remote.TaskManagerService;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.app.TaskLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.UpdateTaskRequest;
import com.honnalmanja.javamvvmpractice.utils.CommonUtils;
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

    private MutableLiveData<TaskLiveData> allTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TaskLiveData> singleTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TaskLiveData> addTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TaskLiveData> updateTaskLiveData = new MutableLiveData<>();
    private MutableLiveData<TaskLiveData> deleteTaskLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public TaskRepository() {
        TaskManager.getApp().getAppComponent().inject(this);
    }

    public MutableLiveData<TaskLiveData> getAllTaskLiveData() {
        return allTaskLiveData;
    }

    public MutableLiveData<TaskLiveData> getSingleTaskLiveData() {
        return singleTaskLiveData;
    }

    public MutableLiveData<TaskLiveData> getAddTaskLiveData() {
        return addTaskLiveData;
    }

    public MutableLiveData<TaskLiveData> getUpdateTaskLiveData() {
        return updateTaskLiveData;
    }

    public MutableLiveData<TaskLiveData> getDeleteTaskLiveData() {
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
                        allTaskLiveData.postValue(new TaskLiveData(
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
                        allTaskLiveData.postValue(new TaskLiveData(
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

    public void getSingleTask(String taskID){
        compositeDisposable.add(
                taskManagerService.getATask(
                        CommonUtils.composeToken(appPreference.gerUserToken()),taskID
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Task>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Task> response) {
                        LogUtil.i(TAG, "Single task Response: " + response);

                        singleTaskLiveData.postValue(new TaskLiveData(
                                response.code(),
                                response.message(),
                                response.body(),
                                null
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG, "Single Task Error");
                        LogUtil.e(TAG, "Throwable: "+e);
                        LogUtil.e(TAG, "Message: "+e.getMessage());
                        singleTaskLiveData.postValue(new TaskLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
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

                    addTaskLiveData.postValue(new TaskLiveData(
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
                    addTaskLiveData.postValue(new TaskLiveData(
                            500,
                            e.getMessage(),
                            null,
                            null
                    ));
                }
            })
        );
    }

    public void updateTask(UpdateTaskRequest updateTaskRequest, String taskID){
        compositeDisposable.add(
                taskManagerService.updateTask(
                        CommonUtils.composeToken(appPreference.gerUserToken()),
                        taskID, updateTaskRequest
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Task>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Task> response) {
                        LogUtil.i(TAG, "Update Task Response: " + response);

                        updateTaskLiveData.postValue(new TaskLiveData(
                                response.code(),
                                response.message(),
                                response.body(),
                                null
                        ));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtil.e(TAG, "UpdateTaskResponse");
                        LogUtil.e(TAG, "Throwable: "+e);
                        LogUtil.e(TAG, "Message: "+e.getMessage());
                        updateTaskLiveData.postValue(new TaskLiveData(
                                500,
                                e.getMessage(),
                                null,
                                null
                        ));
                    }
                })
        );
    }

    public void deleteTask(String taskID){
        compositeDisposable.add(
                taskManagerService.deleteTask(
                        CommonUtils.composeToken(appPreference.gerUserToken()), taskID
                ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Task>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Task> response) {
                        LogUtil.i(TAG, "Delete Task Response: " + response);
                        deleteTaskLiveData.postValue(new TaskLiveData(
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
                        deleteTaskLiveData.postValue(new TaskLiveData(
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
