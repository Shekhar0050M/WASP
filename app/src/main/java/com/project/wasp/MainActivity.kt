package com.project.wasp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.ioutils.AudioUtils


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Audio Utils
//        val audioUtils = AudioUtils(this)
//        audioUtils.startRecording()
        val audioIntent = Intent(this, AudioUtils::class.java)
        startForegroundService(audioIntent)
        //Navigation Component
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
}
