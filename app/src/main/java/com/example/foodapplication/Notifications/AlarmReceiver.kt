package com.example.foodapplication.Notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodapplication.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        sendNotification(context!!, intent!!)
    }

    private fun sendNotification(context: Context?, intent: Intent?){
        val CHANNEL_ID = "channel_id"

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Food Application")
            .setContentText("Remember to record your food intake for today. Tap to open application")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.notify(123, builder.build())
        Toast.makeText(context!!, "Notification sent", Toast.LENGTH_SHORT).show()

    }
}