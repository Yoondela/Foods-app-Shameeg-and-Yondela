package com.example.foodapplication

import okhttp3.Response
import org.json.JSONObject

interface Communicator {

    fun passData(calories: String)

    // we'll have to make changes to this function to update the calories according to what we get back from api
    fun checkCals(response: Response)

    fun resetCals():String

    //a function that gets the json object and returns the JSONObject
    fun getJson():String

}