package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    var calories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goNextPage()

    }

    fun goNextPage(){

        val welcomeFragment = WelcomeFragment()
        val inputFragment = InputFragment()
        val btContinue : Button = findViewById(R.id.btnContinue)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, welcomeFragment)
            commit()
        }

        btContinue.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, inputFragment)
                commit()
                addToBackStack(null)
            }
        }
    }
}