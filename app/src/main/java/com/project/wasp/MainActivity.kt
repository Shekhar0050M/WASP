package com.project.wasp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.wasp.fragment.DetailedInfoFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Navigation Component
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

        // Start the fragment when MainActivity is started
        if (savedInstanceState == null) {
            Log.d("MainActivity","StartedDetailedInfoFragment")
            val fragment = DetailedInfoFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.navigation_detailed, fragment)
                .commit()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

}
