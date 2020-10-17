package com.honnalmanja.javamvvmpractice.model.remote;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.AddTaskRequest;
import com.honnalmanja.javamvvmpractice.model.app.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.remote.tasks.Task;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface TaskManagerService {

    //-------------------------- User Services ------------------------------//
    @POST("users/add")
    Observable<Response<UserResponse>> createUser(@Body CreateUserRequest createUserRequest);

    @POST("users/login")
    Observable<Response<UserResponse>> loginUser(@Body LoginUserRequest loginUserRequest);

    @POST("users/logoutAll")
    Single<Response<UserResponse>> logoutAll(@Header("Authorization") String token);

    @POST("users/logout")
    Single<Response<UserResponse>> logoutUser(@Header("Authorization") String token);
    //-------------------------- User Services ------------------------------//

    //-------------------------- Task Services ------------------------------//
    @GET("tasks")
    Observable<Response<List<Task>>> getAllTasks(@Header("Authorization") String token);

    @POST("tasks/add")
    Single<Response<Task>> addTask(@Header("Authorization") String token, @Body AddTaskRequest addTaskRequest);

    @PATCH("tasks/taskID")
    Single<Response<Task>> updateTask(@Header("Authorization") String token, @Body AddTaskRequest addTaskRequest);

    @DELETE("tasks/taskID")
    Single<Response<Task>> deleteTask(@Header("Authorization") String token);

    //-------------------------- Task Services ------------------------------//

}
