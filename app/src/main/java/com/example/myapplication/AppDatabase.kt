package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}