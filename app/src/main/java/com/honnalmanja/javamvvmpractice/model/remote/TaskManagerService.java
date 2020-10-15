package com.honnalmanja.javamvvmpractice.model.remote;

import com.honnalmanja.javamvvmpractice.model.remote.tasks.TasksResponse;
import com.honnalmanja.javamvvmpractice.model.remote.users.CreateUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.LoginUserRequest;
import com.honnalmanja.javamvvmpractice.model.remote.users.UserResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TaskManagerService {

    @GET("tasks")
    Flowable<TasksResponse> getAllTasks();

    @POST("users/add")
    Single<Response<UserResponse>> createUser(@Body CreateUserRequest createUserRequest);

    @POST("users/login")
    Single<Response<UserResponse>> loginUser(@Body LoginUserRequest loginUserRequest);

}
