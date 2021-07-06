package com.example.foodapplication.progressDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CaloriesDao {

    @Insert
    suspend fun addUserCalories(calories:Calories)

    @Query("select * from calories_table")
    fun readAllData():LiveData<List<Calories>>

}