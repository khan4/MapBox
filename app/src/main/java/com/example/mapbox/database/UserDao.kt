package com.example.mapbox.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity : UserEntity )

    @Query("SELECT * FROM UserInfo")
     fun getAll(): LiveData<List<UserEntity>>

   // @Query("SELECT * FROM UserInfo WHERE userId= :userId")
   // fun findUser(usetId:Int): UserEntity

    @Delete
     fun deleteUser(userEntity: UserEntity)

    @Delete
     fun deleteAllUsers(userEntity: List<UserEntity>)




}