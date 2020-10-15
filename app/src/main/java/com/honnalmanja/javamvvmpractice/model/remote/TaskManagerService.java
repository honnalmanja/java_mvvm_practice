package com.honnalmanja.javamvvmpractice.model.remote;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface TaskManagerService {

    //-------------------------- User Services ------------------------------//
    @POST("users/add")
    Single<Response<UserResponse>> createUser(@Body CreateUserRequest createUserRequest);

    @POST("users/login")
    Single<Response<UserResponse>> loginUser(@Body LoginUserRequest loginUserRequest);
    //-------------------------- User Services ------------------------------//

    //-------------------------- Task Services ------------------------------//
    @GET("tasks")
    Flowable<Response<List<TasksResponse>>> getAllTasks();

    @POST("tasks/add")
    Single<Response<TasksResponse>> addTask(@Body AddTaskRequest addTaskRequest);

    @PATCH("tasks/taskID")
    Single<Response<TasksResponse>> updateTask(@Body AddTaskRequest addTaskRequest);

    @DELETE("tasks/taskID")
    Single<Response<TasksResponse>> deleteTask();

    //-------------------------- Task Services ------------------------------//

}
