package com.honnalmanja.javamvvmpractice.di;

import android.app.Application;

import com.honnalmanja.javamvvmpractice.model.local.prefs.AppPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {

    @Singleton
    @Provides
    AppPreference provideAppPreference(Application application){
        return new AppPreference(application);
    }

}
