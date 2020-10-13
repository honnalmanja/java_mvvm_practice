package com.honnalmanja.javamvvmpractice;

import android.app.Application;

import com.honnalmanja.javamvvmpractice.di.AppComponent;
import com.honnalmanja.javamvvmpractice.di.ApplicationModule;
import com.honnalmanja.javamvvmpractice.di.DaggerAppComponent;

public class TaskManager extends Application {

    private static TaskManager taskManager;
    private AppComponent appComponent;

    public static TaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
