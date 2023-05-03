package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.UserModel

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): LiveData<List<UserModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userModel: UserModel)

    @Delete
    suspend fun delete(userModel: UserModel)
}