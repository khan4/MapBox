package com.example.mapbox.depinjection;

import androidx.lifecycle.ViewModel;

import com.example.mapbox.MainActivity;
import com.example.mapbox.mapactivity.MyMapActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {
            UserViewModelModule.class, UserApiModeule.class
    })
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract MyMapActivity contributrMyMapActivity();
}
