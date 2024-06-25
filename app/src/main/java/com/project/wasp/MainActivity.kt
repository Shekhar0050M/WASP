package com.project.wasp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.fragment.DetailedInfoFragment
import com.project.wasp.systemutils.AppForegroundService
import com.project.wasp.systemutils.PermissionsHelper

class MainActivity : AppCompatActivity() {

    private lateinit var permissionsHelper: PermissionsHelper

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
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


    override fun onDestroy() {
        super.onDestroy()
    }
}
