package com.honnalmanja.javamvvmpractice.model.app;

import com.honnalmanja.javamvvmpractice.model.remote.users.User;

import java.util.List;

public class UserLiveData {

    private int statusCode;
    private String message;
    private User user;
    private List<User> userList;
    private String token;

    public UserLiveData(int statusCode, String message, User user, List<User> userList) {
        this.statusCode = statusCode;
        this.message = message;
        this.user = user;
        this.userList = userList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", userList=" + userList +
                '}';
    }
}
