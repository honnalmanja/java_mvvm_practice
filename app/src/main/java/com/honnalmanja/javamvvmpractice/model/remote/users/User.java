package com.honnalmanja.javamvvmpractice.model.remote.users;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    private String userID;

    @SerializedName("name")
    private String userName;

    @SerializedName("email")
    private String userEmail;

    @SerializedName("age")
    private int userAge;

    @SerializedName("avatar")
    private String avatarURL;

    public User(String userID, String userName, String userEmail, int userAge, String avatarURL) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.avatarURL = avatarURL;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAge=" + userAge +
                ", avatarURL='" + avatarURL + '\'' +
                '}';
    }
}
