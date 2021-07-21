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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class UserNotification : Fragment() {

    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    fun createNotificationChannel(){
        val CHANNEL_ID = "channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Food Application"
            val descriptionText = "Remember to record your food intake for today. Tap to open application"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

        picker.show(requireActivity().supportFragmentManager, "channel_id")

        picker.addOnPositiveButtonClickListener{

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }
    }

    fun setAlarm() {

        alarmManager = activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(),0, intent,0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(requireContext(), "Notifications set", Toast.LENGTH_SHORT).show()

    }

    fun cancelAlarm() {
        alarmManager = activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(),0, intent,0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(requireContext(),"Notifications canceled", Toast.LENGTH_SHORT).show()
    }
}