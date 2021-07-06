package com.example.foodapplication.progressDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CaloriesViewModel(application: Application):AndroidViewModel(application) {

    private val readAllData:LiveData<List<Calories>>
    private val repo:CaloriesRepository

    init{
        val caloriesDao = CaloriesDatabase.getIntakeDatabase(application).userIntakeDao()
        repo = CaloriesRepository(caloriesDao)
        readAllData = repo.readAllData
    }

    fun addCalories(calories: Calories){
        viewModelScope.launch(Dispatchers.IO){
            repo.addCalories(calories)
        }
    }
}
