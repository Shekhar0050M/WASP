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
            "Service Running",
            "Foreground Service is running",
            intent
        )

        // Make the service a foreground service
        startForeground(1, notificationHelper.buildNotification(
            "Service Running",
            "Foreground Service is running",
            intent
        ))
        Log.d("Notification", "Notification created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ForegroundService", "Service started")

        // Add your monitoring logic here

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
