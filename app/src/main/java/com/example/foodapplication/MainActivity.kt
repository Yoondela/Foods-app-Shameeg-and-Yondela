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

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }

    }
}