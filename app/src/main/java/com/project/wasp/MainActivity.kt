package com.project.wasp

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
//import com.chaquo.python.Python
//import com.chaquo.python.android.AndroidPlatform
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.systemutils.AppForegroundService
import com.project.wasp.systemutils.PermissionsHelper
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var timeReceiver: BroadcastReceiver

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setThemeBasedOnTime()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check and request permissions
        permissionsHelper = PermissionsHelper(this)

        // Check and request permissions
        val permissionsNeeded = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.POST_NOTIFICATIONS
        )
        permissionsHelper.checkAndRequestPermissions(permissionsNeeded)

        //Navigation Component
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        // Theme Implementation
        timeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                setThemeBasedOnTime()
                recreate() // Restart activity to apply new theme
            }
        }

        registerReceiver(timeReceiver, IntentFilter(Intent.ACTION_TIME_TICK))


        // Start the app foreground service
        Log.d("MainActivity","Started App Foreground Service")
        val serviceIntent = Intent(this, AppForegroundService::class.java)
        startForegroundService(serviceIntent)

        // Python code run
//        onCreatePythonRun()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsHelper.handlePermissionsResult(requestCode, permissions, grantResults)
    }


    private fun setThemeBasedOnTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        when (hour) {
            in 1..3 -> setTheme(R.style.Theme_EarlyNight)
            in 3..5 -> setTheme(R.style.Theme_LateNight)
            in 5..6 -> setTheme(R.style.Theme_EarlyMorning)
            in 6..12 -> setTheme(R.style.Theme_LateMorning)
            in 12..15 -> setTheme(R.style.Theme_EarlyAfternoon)
            in 15..18 -> setTheme(R.style.Theme_LateAfternoon)
            in 18..21  -> setTheme(R.style.Theme_EarlyEvening)
            in 21..23 -> setTheme(R.style.Theme_LateEvening)
            else -> setTheme(R.style.Theme_TrueColors)
        }
    }

//    private fun onCreatePythonRun(){
//        if (! Python.isStarted()) {
//            Python.start(AndroidPlatform(applicationContext))
//        }
//        val python = Python.getInstance()
//        val module = python.getModule("interpreter")
//        val image = module["fig"]
//        Log.d("MainActivity", image.toString())
//    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeReceiver)
    }
}

