package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    var calories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View?) {

        var mockFoodData: HashMap<String, Int> = hashMapOf("apple" to 95, "banana" to 89, "egg" to 155)
        var editTextFood: EditText = findViewById(R.id.txtFood)
        var amount: EditText = findViewById(R.id.txtAmount)
        var textViewOutput: TextView = findViewById(R.id.tViewFoods)
        val food = editTextFood.text
        val foodAmount = amount.text

        when {
            food.toString() == "Apple" -> {
                calories += if(foodAmount.toString()  == ""){
                    mockFoodData["apple"]!!
                } else{
                    mockFoodData["apple"]?.times(foodAmount.toString().toInt())!!
                }
            }
            food.toString() == "Banana" -> {
                calories += if(foodAmount.toString()  == ""){
                    mockFoodData["banana"]!!
                } else{
                    mockFoodData["banana"]?.times(foodAmount.toString().toInt())!!
                }
            }
            food.toString() == "Egg" -> {
                calories += if(foodAmount.toString()  == ""){
                    mockFoodData["egg"]!!
                } else{
                    mockFoodData["egg"]?.times(foodAmount.toString().toInt())!!
                }
            }
        }

        var textViewCalories : TextView = findViewById(R.id.textView2)

        textViewCalories.text = calories.toString()
        textViewOutput.append("$food $foodAmount\n")
    }
}