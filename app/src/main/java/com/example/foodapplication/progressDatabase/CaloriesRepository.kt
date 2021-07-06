package com.example.foodapplication.progressDatabase

import androidx.lifecycle.LiveData

class CaloriesRepository(private val caloriesDao: CaloriesDao) {

    val readAllData:LiveData<List<Calories>> = caloriesDao.readAllData()

    suspend fun addCalories(calories: Calories){
        caloriesDao.addUserCalories(calories)
    }
}