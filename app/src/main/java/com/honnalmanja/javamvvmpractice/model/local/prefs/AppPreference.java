package com.honnalmanja.javamvvmpractice.model.local.prefs;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.model.remote.users.User;

public class AppPreference {

    private static final String TAG = AppPreference.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    private Application application;

    public AppPreference(Application application){
        this.application = application;
        init();
        Log.i(TAG, "AppPreference constructor");
    }

    private void init(){
        sharedPreferences = application.getSharedPreferences(
                application.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void saveUserToken(String userID){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.key_user_token), userID);
        editor.apply();
    }

    public String gerUserToken(){
        return sharedPreferences.getString(
                application.getString(R.string.key_user_token), "");
    }

    public void saveUserDetails(User user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(application.getString(R.string.key_logged_in_user), json);
        editor.apply();
    }

    public User getUserDetails(){
        Gson gson = new Gson();
        return gson.fromJson(sharedPreferences.getString(
                application.getString(R.string.key_logged_in_user), null), User.class);
    }

}
