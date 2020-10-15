package com.honnalmanja.javamvvmpractice.model.remote.users;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private User user;

    public UserResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
