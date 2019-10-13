package com.example.mapbox.depinjection

import com.example.mapbox.networking.UserApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UserApiModeule {

    @Provides
    internal fun provideUserApi(retrofit: Retrofit) :UserApi{
        return retrofit.create(UserApi::class.java)
    }


}