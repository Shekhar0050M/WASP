package com.project.wasp.systemutils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.project.wasp.R

class ForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d("ForegroundService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ForegroundService", "Service started")
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Wellness and Security App")
            .setContentText("Monitoring your environment")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        startForeground(NOTIFICATION_ID, notification)

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

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}
