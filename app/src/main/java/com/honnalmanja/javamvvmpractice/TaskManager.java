package com.honnalmanja.javamvvmpractice;

import android.app.Application;

import com.honnalmanja.javamvvmpractice.di.AppComponent;
import com.honnalmanja.javamvvmpractice.di.ApplicationModule;
import com.honnalmanja.javamvvmpractice.di.DaggerAppComponent;
import com.honnalmanja.javamvvmpractice.di.PreferenceModule;
import com.honnalmanja.javamvvmpractice.di.RetrofitModule;

public class TaskManager extends Application {

    private static TaskManager app;
    private AppComponent appComponent;

    public static TaskManager getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        appComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .preferenceModule(new PreferenceModule())
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
