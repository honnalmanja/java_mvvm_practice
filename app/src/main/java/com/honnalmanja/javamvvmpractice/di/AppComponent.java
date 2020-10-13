package com.honnalmanja.javamvvmpractice.di;

import com.honnalmanja.javamvvmpractice.model.repository.AppRepository;
import com.honnalmanja.javamvvmpractice.view.user.LoginActivity;
import com.honnalmanja.javamvvmpractice.view.task.TaskActivity;
import com.honnalmanja.javamvvmpractice.view.user.SignUpActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, RetrofitModule.class, PreferenceModule.class})
public interface AppComponent {

    void inject(AppRepository appRepository);

}
