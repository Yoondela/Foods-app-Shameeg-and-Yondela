package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(v: View?) {

        var ourListOfFood: HashMap<String, Int> = hashMapOf("apple" to 95, "banana" to 89, "egg" to 155)
        var editTextFood: EditText = findViewById(R.id.txtFood)
        var amount: EditText = findViewById(R.id.txtAmount)
        var textViewOutput: TextView = findViewById(R.id.tViewFoods)
        val food = editTextFood.text
        val foodAmount = amount.text
        var calories = 0

        if(food.toString() == "apple"){
            calories += ourListOfFood["apple"]!!
        }

        var textViewCalories : TextView = findViewById(R.id.textView2)

        textViewCalories.append(calories.toString())
        textViewOutput.append("$food $foodAmount\n")
    }
}