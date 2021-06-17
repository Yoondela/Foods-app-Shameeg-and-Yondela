package com.example.foodapplication

interface Communicator {

    fun passData(listOfCalories:ArrayList<String>)
    fun goToRegister()
    fun goToLogin()
    fun goToInputFrag()

}