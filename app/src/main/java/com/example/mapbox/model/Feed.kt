package com.example.mapbox.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 class Feed {

    @SerializedName("id")
    @Expose
    var userId: Int? =null

    @SerializedName("name")
    @Expose
    var userName: String? = null

    @SerializedName("latitude")
    @Expose
    var userLatitude: Double? = null

    @SerializedName("longitude")
    @Expose
    var userLongitude: Double? =null

    @SerializedName("description")
    @Expose
    var userDescription: String? = null

    var checkId:Int=0



}