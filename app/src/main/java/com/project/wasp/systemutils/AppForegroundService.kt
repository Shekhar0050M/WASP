package com.project.wasp.systemutils

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.project.wasp.MainActivity
import com.project.wasp.R
import com.project.wasp.ioutils.AudioUtils
import com.project.wasp.utils.SharedPreferencesManager

class AppForegroundService : Service() {

    private lateinit var notificationHelper: NotificationHelper
    private lateinit var audioRecorder: AudioUtils
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000L // Update every second (1000 milliseconds)

    override fun onCreate() {
        super.onCreate()
        Log.d("AppForegroundService", "Service created")
        notificationHelper = NotificationHelper(this)

        // Show a notification when the service is created
        val intent = Intent(this, MainActivity::class.java)
        notificationHelper.showNotification(
            "WASP",
            "Tracking environment\uD83D\uDE0A",
            intent
        )

        // Make the service a foreground service
        startForeground(1, notificationHelper.buildNotification(
            "WASP",
            "Tracking environment\uD83D\uDE0A",
            intent
        ))
        Log.d("Notification", "Notification created")

        // Initialize SharedPreferencesManager and AudioUtils
        sharedPreferencesManager = SharedPreferencesManager(this)
        audioRecorder = AudioUtils(this)

        // Start updating amplitude
        handler.post(updateRunnable)
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            val amplitude = audioRecorder.calculateAmplitude()
            val amplitudeString = buildString {
                append(getString(R.string.audio_amplitude_text))
                append(": ")
                append(amplitude)
            }

//            val averageAmplitude = audioRecorder.calculateAmplitude()
            val averageAmplitudeString = buildString {
                append(getString(R.string.average_audio_amplitude_text))
                append(": ")
                append("0.0")
            }

            // Save amplitudeString in SharedPreferences
            sharedPreferencesManager.saveValue("amplitudeText", amplitudeString)
            sharedPreferencesManager.saveValue("averageAmplitudeText", averageAmplitudeString)

            // Schedule the next update
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppForegroundService", "Service started")

        // Add your monitoring logic here
        audioRecorder.startRecording()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the handler and audio recording
        handler.removeCallbacks(updateRunnable)
        audioRecorder.stopRecording()
        Log.d("AppForegroundService", "Service destroyed")
    }

}

