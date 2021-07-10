package com.example.foodapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodapplication.fragments.LoginFragment
import java.util.*
import kotlin.collections.ArrayList
import com.example.foodapplication.Notifications.AlarmReceiver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setDefaultScreen()
        }
        val numberOfNotifications = 2
        createNotificationChannel()
        setAlarm(numberOfNotifications)
    }

    private fun setDefaultScreen() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainLayout, loginFragment)
            commit()
        }
    }

    private fun createNotificationChannel(){
        val CHANNEL_ID = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Food Application"
            val descriptionText = "Remember to record your food intake for today. Tap to open application"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setAlarm(number: Int){
        val alarmReceiver = AlarmReceiver()
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val now = Calendar.getInstance()
        val calendarList = ArrayList<Calendar>()

        for(i in 1..number){
            calendarList.add(now)
        }
        for(calendar in calendarList){
            calendar.add(Calendar.SECOND,10) // still need to get this to be a specific time of the day
            val requestCode = Random().nextInt() // just a request id
            val intent = Intent(this, alarmReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            val pi = PendingIntent.getBroadcast(this, requestCode, intent, 0)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pi)
            else
                am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,pi)

            Toast.makeText(this, "Alarm has been set", Toast.LENGTH_SHORT).show()
        }
    }

}