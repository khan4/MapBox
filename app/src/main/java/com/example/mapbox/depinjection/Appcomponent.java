package com.example.mapbox.depinjection;

import android.app.Application;


import com.example.mapbox.factory.ViewModelFactoryModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class,
                      ActivityBuilderModule.class,
                      AppModule.class,
                      ViewModelFactoryModule.class
})
public interface Appcomponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        Appcomponent build();
    }

}
