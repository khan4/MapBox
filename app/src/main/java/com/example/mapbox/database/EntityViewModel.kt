package com.example.mapbox.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class EntityViewModel(application: Application) : AndroidViewModel(application) {


    private var repository = Repository(application)
    private var entitlList : LiveData<List<UserEntity>>  = repository.getAllEnities()


    fun insert(userEntity: UserEntity){
        repository.insertData(userEntity)
    }

    fun deleteAll(){
      //  repository.deleteAll()
    }


    fun observeEntries()=entitlList
}