package com.example.foodapplication

interface Communicator {

    fun passData(calories: String)

    fun resetCals():Double

    fun getJson():Double

    fun getFoodList():ArrayList<String>

}