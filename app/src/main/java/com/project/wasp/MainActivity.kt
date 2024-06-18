package com.project.wasp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.ioutils.AudioUtils
import com.project.wasp.utils.SharedPreferencesManager


class MainActivity : AppCompatActivity() {

    private lateinit var audioRecorder: AudioUtils
    private lateinit var amplitudeTextView: TextView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Navigation Component
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(this)

        // Initialize AudioUtils and TextView
        audioRecorder = AudioUtils(this)
        amplitudeTextView = findViewById(R.id.audioUtils)

        // Start recording
        audioRecorder.startRecording()

        // Handler to update amplitudeTextView and save amplitudeString in SharedPreferences
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val amplitude = audioRecorder.calculateAmplitude()
                val amplitudeString = buildString {
                    append(getString(R.string.audio_amplitude_text))
                    append(": ")
                    append(amplitude)
                }

                // Update amplitudeTextView
                amplitudeTextView.text = sharedPreferencesManager.getValue("amplitudeText",
                    getString(R.string.audiotechnicaltext))
                Log.d("SharedPreferencesManager", "Saving value: $amplitudeString with key: amplitudeText")
                // Save amplitudeString in SharedPreferences
                sharedPreferencesManager.saveValue("amplitudeText", amplitudeString)

                // Schedule the next update
                handler.postDelayed(this, 1000) // Update every second (1000 milliseconds)
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        audioRecorder.stopRecording() // Stop recording when MainActivity is destroyed
    }
}
