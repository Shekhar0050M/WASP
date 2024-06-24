package com.project.wasp.systemutils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsHelper(private val activity: Activity) {

    private val PERMISSION_REQUEST_CODE = 1
    private val RECORD_AUDIO_PERMISSION_CODE = 1001

    fun checkAndRequestPermissions(permissionsNeeded: Array<String>) {
        val permissionsToRequest = mutableListOf<String>()

        for (permission in permissionsNeeded) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    fun handlePermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, handle accordingly
                } else {
                    // Permission denied, handle accordingly

                        // Request the permission with FLAG_ONE_SHOT (Allow always)
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            RECORD_AUDIO_PERMISSION_CODE
                        )
                }
            }
        }
    }
}
