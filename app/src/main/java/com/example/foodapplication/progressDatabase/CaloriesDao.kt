package com.example.foodapplication.progressDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CaloriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserCalories(calories:Calories)

    @Query("select * from calories_table")
    fun readAllData():LiveData<List<Calories>>

    @Query("select * from calories_table where userEmail = :email")
    fun readUserCalories(email:String):LiveData<List<Calories>>
}