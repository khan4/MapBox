package com.example.mapbox.depinjection;

import androidx.lifecycle.ViewModel;

import com.example.mapbox.mvvmviewmodel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class UserViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    public abstract ViewModel bindsUserViewModel(UserViewModel userViewModel);
}
