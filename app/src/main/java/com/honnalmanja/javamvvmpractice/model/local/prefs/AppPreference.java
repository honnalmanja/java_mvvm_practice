package com.honnalmanja.javamvvmpractice.model.local.prefs;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.honnalmanja.javamvvmpractice.R;

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

    public void saveUserID(String userID){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(application.getString(R.string.key_user_id), userID);
        editor.apply();
    }

    public String gerUserID(){
        return sharedPreferences.getString(
                application.getString(R.string.key_user_id), "");
    }

}
