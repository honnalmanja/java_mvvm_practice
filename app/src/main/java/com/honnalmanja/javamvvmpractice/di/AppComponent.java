package com.honnalmanja.javamvvmpractice.di;

import com.honnalmanja.javamvvmpractice.model.repository.TaskRepository;
import com.honnalmanja.javamvvmpractice.model.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class, RetrofitModule.class, PreferenceModule.class})
public interface AppComponent {

    void inject(TaskRepository taskRepository);

    void inject(UserRepository userRepository);

}
