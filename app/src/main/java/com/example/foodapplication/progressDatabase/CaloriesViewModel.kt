package com.example.foodapplication.progressDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CaloriesViewModel(application: Application):AndroidViewModel(application) {

    private val repo:CaloriesRepository

    init{
        val caloriesDao = CaloriesDatabase.getIntakeDatabase(application).userIntakeDao()
        repo = CaloriesRepository(caloriesDao)
    }

    fun addCalories(calories: Calories){
        viewModelScope.launch(Dispatchers.IO){
            repo.addCalories(calories)
        }
    }

    fun readUserCalories(email:String)=
        repo.readUserCalories(email)

    fun readUserEntryDate(email:String, date: Float)=
        repo.readUserEntryDate(email,date)
}
