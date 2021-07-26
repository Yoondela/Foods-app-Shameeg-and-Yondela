package com.example.foodapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.foodapplication.fragments.LoginFragment
import java.util.*
import kotlin.collections.ArrayList
import com.example.foodapplication.Notifications.AlarmReceiver
import com.example.foodapplication.Notifications.UserNotification
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userNotification = UserNotification(this.applicationContext)

        calendar = Calendar.getInstance()
        if (savedInstanceState == null) {
            setDefaultScreen()
        }
        userNotification.createNotificationChannel()
    }

    private fun setDefaultScreen() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, loginFragment)
            commit()
        }
    }
}