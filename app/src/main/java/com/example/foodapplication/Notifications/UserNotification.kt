package com.example.foodapplication.Notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapplication.MainActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class UserNotification {
    
    private val context: Context
    
    constructor(context:Context){
        this.context = context
    }
    
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun createNotificationChannel() {
        val CHANNEL_ID = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Food Application"
            val descriptionText =
                "Remember to record your food intake for today. Tap to open application"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTimePicker(){
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Notification Time")
            .build()

        picker.show((context as MainActivity).supportFragmentManager, "channel_id")

        picker.addOnPositiveButtonClickListener{

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            setAlarm(calendar)
        }
    }

    private fun setAlarm(calendar: Calendar) {
//        calendar = Calendar.getInstance()
        alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context,0, intent,0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Notifications set", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm() {
        alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(context,0, intent,0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context,"Notifications canceled", Toast.LENGTH_SHORT).show()
    }

    fun getTodayDate(): String {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val date = Date()
        return (sdf.format(date))
    }
}