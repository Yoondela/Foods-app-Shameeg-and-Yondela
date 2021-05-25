package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var editTextFood: EditText
    lateinit var  buttonClickMe: Button
    lateinit var amount: EditText
    lateinit var textViewOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextFood = findViewById(R.id.txtFood)
        buttonClickMe = findViewById(R.id.btnAdd)
        textViewOutput = findViewById(R.id.tViewFoods)
        amount = findViewById(R.id.txtAmount)

        buttonClickMe.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val food = editTextFood.text
        val foodAmount = amount.text
        textViewOutput.append("$food $foodAmount\n")
    }
}