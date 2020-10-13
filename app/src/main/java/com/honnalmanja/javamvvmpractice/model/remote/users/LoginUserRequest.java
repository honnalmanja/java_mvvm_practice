package com.honnalmanja.javamvvmpractice.model.remote.users;

import com.google.gson.annotations.SerializedName;

public class LoginUserRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginUserRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
