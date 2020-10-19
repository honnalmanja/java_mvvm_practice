package com.honnalmanja.javamvvmpractice.di;

import android.app.Application;

import com.honnalmanja.javamvvmpractice.model.local.db.AppDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "TaskManagerDB").build();
    }

}
