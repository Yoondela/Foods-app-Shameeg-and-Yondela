package com.example.foodapplication.Notifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodapplication.MainActivity
import com.example.foodapplication.R
import com.example.foodapplication.fragments.InputFragment
import com.example.foodapplication.fragments.LoginFragment

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        sendNotification(context, intent)
        Toast.makeText(context!!, "Notification sent", Toast.LENGTH_SHORT).show()

    }

    private fun sendNotification(context: Context?, intent: Intent?){
        val CHANNEL_ID = "channel_id"
        val userNotification = UserNotification(context!!)
        val todayDate: String = userNotification.getTodayDate()

        val _intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, _intent,0)


        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("Food Application")
            .setStyle(NotificationCompat.BigTextStyle().bigText("Remember to record your food intake for today ($todayDate)\nTap to open application"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.notify(123, builder.build())
        Toast.makeText(context!!, "Notification sent", Toast.LENGTH_SHORT).show()

    }
}