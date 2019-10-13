package com.example.mapbox.networking

import com.example.mapbox.model.Feed
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {

    @GET("get_map_pins.php")
    fun getData() : Flowable<List<Feed>>

    var id :Int
}