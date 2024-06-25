package com.project.wasp.systemutils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.project.wasp.MainActivity

class ForegroundService : Service() {

    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        Log.d("ForegroundService", "Service created")
        notificationHelper = NotificationHelper(this)

        // Show a notification when the service is created
        val intent = Intent(this, MainActivity::class.java)
        notificationHelper.showNotification(
            "WASP",
            "Have a Good Day\uD83D\uDE0A",
            intent
        )

        // Make the service a foreground service
        startForeground(1, notificationHelper.buildNotification(
            "WASP",
            "Have a Good Day\uD83D\uDE0A",
            intent
        ))
        Log.d("Notification", "Notification created")

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ForegroundService", "Service started")

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d("ForegroundService", "Service destroyed")
    }

}
