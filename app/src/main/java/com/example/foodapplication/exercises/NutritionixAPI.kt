package com.example.foodapplication.exercises

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class NutritionixAPI(totalCalories: Double) {

    private lateinit var myResponse: String

    var exerciseType: String = ""
    var exerciseAmount = 0
    var totalCalories = totalCalories


    fun makeApiCall(query: String){
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "query=1 min $query")
        val request = Request.Builder()

            .url("https://trackapi.nutritionix.com/v2/natural/exercise")
            .method("POST", body)
            .addHeader("x-app-id", "ef9a1a8a")
            .addHeader("x-app-key", "b56e54c71476cbc183f3f40ae2ae49d3")
            .addHeader("content", "application/json")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()

        client.newCall(request).enqueue((object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                myResponse = response.body()!!.string()
                val gson = GsonBuilder().create()
                var exercisesData = gson.fromJson(myResponse, ExercisesInfo::class.java)
                val exercisesItems = gson.fromJson(exercisesData.exercises.toString().trim('[',']'), ExercisesInfoItems::class.java)

                val type = exercisesItems.name
                set_exercise_type(type)

                var nfCalories = exercisesItems.nf_calories
                exerciseAmount = getRequiredExerciseDuration(nfCalories, totalCalories)
            }
        }))
    }

    fun getRequiredExerciseDuration(caloriesPerMin: Double, totalCalories: Double): Int{

        return if (totalCalories > 0) {
            var caloriesIncr = caloriesPerMin
            var minIncr = 1

            while (caloriesIncr <= totalCalories) {
                caloriesIncr = caloriesIncr + caloriesPerMin
                minIncr++
            }

            minIncr
        }else 0
    }

    private fun set_exercise_type(type: String){
        this.exerciseType = type
    }

    fun get_exercise_type(): String{
        return this.exerciseType
    }
}