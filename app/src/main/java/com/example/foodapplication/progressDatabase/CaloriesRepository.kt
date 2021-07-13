package com.example.foodapplication.progressDatabase

class CaloriesRepository(private val caloriesDao: CaloriesDao) {

    suspend fun addCalories(calories: Calories){
        caloriesDao.addUserCalories(calories)
    }

    fun readUserCalories(email:String) =
        caloriesDao.readUserCalories(email)


}