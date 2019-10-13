package com.example.mapbox.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1,exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    private static UserDataBase instance;
    private static final Object LOCK = new Object();
    public static final String DATABASE_NAME ="ToDoList";

    public static  UserDataBase getInstance(Context context){

        if (instance == null){
         synchronized (LOCK) {
             instance = Room.databaseBuilder(context.getApplicationContext(),
                     UserDataBase.class, DATABASE_NAME)
                     .fallbackToDestructiveMigration()
                     .build();
         }
        }
        return instance;
    }

    public abstract UserDao userDao();

}
