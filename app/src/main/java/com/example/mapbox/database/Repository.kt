package com.example.mapbox.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.mapbox.model.Feed

class Repository(application :Application) {
    private var db : UserDataBase = UserDataBase.getInstance(application)
    private var userDao : UserDao = db.userDao()
    private var userList : LiveData<List<UserEntity>> = userDao.getAll()


    fun insertData(userEntity: UserEntity){
        AppExecuter.getInstance().diskIO().execute(Runnable {
            userDao.insert(userEntity)
        })
    }

    fun deleteAll(){
       // AppExecuter.getInstance().diskIO().execute(Runnable {
      //      userDao.deleteAllUsers(userList.value!!)
      //  })
    }

    fun getAllEnities() = userList





}