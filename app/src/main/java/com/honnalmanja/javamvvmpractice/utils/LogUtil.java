package com.honnalmanja.javamvvmpractice.utils;

import android.util.Log;

import com.honnalmanja.javamvvmpractice.BuildConfig;

public class LogUtil {

    public static void d(String TAG, String message){
        if(BuildConfig.DEBUG){
            Log.d(TAG, message);
        }
    }

    public static void e(String TAG, String message){
        if(BuildConfig.DEBUG){
            Log.e(TAG, message);
        }
    }

    public static void i(String TAG, String message){
        if(BuildConfig.DEBUG){
            Log.i(TAG, message);
        }
    }

}
