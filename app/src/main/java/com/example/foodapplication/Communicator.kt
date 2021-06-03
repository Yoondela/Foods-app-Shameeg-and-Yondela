package com.example.foodapplication

interface Communicator {
    fun passData(calories: String)

    fun checkCals():Int

    fun resetCals():Int
}