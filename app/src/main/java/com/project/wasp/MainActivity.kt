package com.project.wasp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.ioutils.AudioUtils


class MainActivity : AppCompatActivity() {

    private lateinit var audioRecorder: AudioUtils
    private lateinit var amplitudeTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Audio Utils
        audioRecorder = AudioUtils(this)
        amplitudeTextView = findViewById(R.id.audioUtils)
        audioRecorder.startRecording()
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                val amplitude = audioRecorder.calculateAmplitude()
                amplitudeTextView.text = buildString {
                    append(getString(R.string.audio_amplitude_text))
                    append(": ")
                    append(amplitude)
                }
                handler.postDelayed(this, 1000) // Update every second (1000 milliseconds)
            }
        })
        //Navigation Component
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
    override fun onDestroy() {
        super.onDestroy()
        audioRecorder.stopRecording() // Stop recording when MainActivity is destroyed
    }
}
