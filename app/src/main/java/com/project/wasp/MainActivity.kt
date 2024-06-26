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
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.fragment.DetailedInfoFragment
import com.project.wasp.systemutils.AppForegroundService
import com.project.wasp.systemutils.PermissionsHelper
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsHelper: PermissionsHelper
    private lateinit var timeReceiver: BroadcastReceiver

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

        // Activate the Detailed Info fragment
        Log.d("MainActivity","StartedDetailedInfoFragment")
        val fragment = DetailedInfoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.navigation_detailed, fragment)
            .commit()

        // Start the app foreground service
        Log.d("MainActivity","Started App Foreground Service")
        val serviceIntent = Intent(this, AppForegroundService::class.java)
        startForegroundService(serviceIntent)
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
            in 6..8 -> setTheme(R.style.Theme_Morning)
            in 9..11 -> setTheme(R.style.Theme_LateMorning)
//            in 12..14 -> setTheme(R.style.Theme_Afternoon)
//            in 15..17 -> setTheme(R.style.Theme_LateAfternoon)
//            in 18..20 -> setTheme(R.style.Theme_Evening)
//            in 21..23 -> setTheme(R.style.Theme_EarlyNight)
//            in 0..0 -> setTheme(R.style.Theme_Midnight)
//            in 0..2 -> setTheme(R.style.Theme_Night)
//            else -> setTheme(R.style.Theme_LateNight)
            else -> setTheme(R.style.Theme_Night)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeReceiver)
    }
}
