package com.honnalmanja.javamvvmpractice.viewmodel;

import com.honnalmanja.javamvvmpractice.model.app.TaskLiveData;
import com.honnalmanja.javamvvmpractice.model.app.UserLiveData;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.UpdateTaskRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.User;
import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.rxjava3.core.Single;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private Task task;

    public TaskViewModel() {
        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public MutableLiveData<TaskLiveData> getAllTaskLiveData() {
        return taskRepository.getAllTaskLiveData();
    }

    public MutableLiveData<UserLiveData> subscribeLogoutResponse(){
        return userRepository.getLogoutResponseLiveData();
    }

    public MutableLiveData<TaskLiveData> subscribeUpdateTaskLiveData() {
        return taskRepository.getUpdateTaskLiveData();
    }

    public MutableLiveData<TaskLiveData> subscribeDeleteTaskLiveData() {
        return taskRepository.getDeleteTaskLiveData();
    }

    public MutableLiveData<TaskLiveData> subscribeSingleTaskLiveData(){
        return taskRepository.getSingleTaskLiveData();
    }

    public void postTaskDescription(String description, boolean isCompleted){
        taskRepository.postTask(new AddTaskRequest(description, isCompleted));
    }

    public MutableLiveData<TaskLiveData> subscribeAddTaskLiveData() {
        return taskRepository.getAddTaskLiveData();
    }

    public void askForAllTask(){
        taskRepository.askForAllTask();
    }

    public void updateTask(Task task){
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(
                task.getTaskDescription(), task.isTaskCompleted()
        );
        taskRepository.updateTask(updateTaskRequest, task.getTaskID());
    }

    public void deleteTask(String taskID){
        taskRepository.deleteTask(taskID);
    }

    public void getSingleTask(String taskID){
        taskRepository.getSingleTask(taskID);
    }

    public void postLogout(){
        userRepository.postLogoutRequest();
    }

    public Single<User> getSavedUser(){
        return userRepository.getSavedUser();
    }

    public void clear(){
        userRepository.clear();
        taskRepository.clear();
    }

}
