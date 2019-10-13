package com.example.mapbox.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
 data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    val userId:Int,

    @ColumnInfo(name = "Col_User_Name")
    val userName: String,

    @ColumnInfo(name = "Col_Latitude")
    val latitude: Double,

    @ColumnInfo(name = "Col_Longitude")
    val longitude: Double,

    @ColumnInfo(name = "Col_Description")
    val description: String
) {


}