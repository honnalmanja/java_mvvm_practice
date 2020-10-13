package com.honnalmanja.javamvvmpractice.model.app;

public class User {

    private String userID;
    private String userName;
    private String userEmail;
    private int userAge;
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
