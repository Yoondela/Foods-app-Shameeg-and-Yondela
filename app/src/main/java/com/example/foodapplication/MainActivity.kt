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

        var editTextFood: EditText = findViewById(R.id.txtFood)
        var amount: EditText = findViewById(R.id.txtAmount)
        var textViewOutput: TextView = findViewById(R.id.tViewFoods)
        val food = editTextFood.text
        val foodAmount = amount.text

        textViewOutput.append("$food $foodAmount\n")
    }
}