package com.example.mapbox.factory

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindsViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}