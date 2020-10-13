package com.honnalmanja.javamvvmpractice.di;

import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.view.task.TaskActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, RetrofitModule.class})
public interface AppComponent {

    void inject(TaskActivity taskActivity);

    void inject(LoginActivity loginActivity);

}
