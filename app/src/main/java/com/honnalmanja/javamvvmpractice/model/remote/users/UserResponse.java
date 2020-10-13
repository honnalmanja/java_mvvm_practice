package com.honnalmanja.javamvvmpractice.model.remote.users;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("_id")
    private String userID;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    public UserResponse(String userID, String email, String password, String name, int age) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userID='" + userID + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
