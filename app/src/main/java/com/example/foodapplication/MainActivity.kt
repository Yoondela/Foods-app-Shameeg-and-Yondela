package com.example.foodapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapplication.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setDefaultScreen()
        }
    }

    private fun setDefaultScreen() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, loginFragment)
            commit()
        }
    }
}