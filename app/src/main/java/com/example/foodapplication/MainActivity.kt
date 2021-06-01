package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDefaultScreen()
    }

    private fun setDefaultScreen(){

        val inputFragment = InputFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, inputFragment)
            commit()
        }
    }
}