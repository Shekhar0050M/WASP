package com.project.wasp.systemutils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.project.wasp.R

class ForegroundService : Service() {
    override fun onCreate() {
        super.onCreate()
        // Perform initialization here
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Start the service in foreground mode
        startForeground(NOTIFICATION_ID, createNotification())

        // Return START_STICKY to restart the service if it's killed by the system
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources here
    }

    private fun createNotification(): Notification {
        // Create and return a notification for the foreground service
        // Example: Create a basic notification
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("WASP")
            .setContentText("Running in background")
            .setSmallIcon(R.drawable.ic_notification)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}
