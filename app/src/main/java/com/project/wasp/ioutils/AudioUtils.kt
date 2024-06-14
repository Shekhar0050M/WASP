package com.project.wasp.ioutils

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast

class AudioUtils (val context: Context){
    companion object {
        private const val RECORD_AUDIO_PERMISSION_CODE = 1001
    }

    init {
        // Check if the permission is already granted
        if (!isRecordAudioPermissionGranted()) {
            requestRecordAudioPermission()
        }
    }

    private fun isRecordAudioPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestRecordAudioPermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_AUDIO_PERMISSION_CODE
        )
    }

    fun startRecording() {
        if (isRecordAudioPermissionGranted()) {
            Toast.makeText(context, "Audio permission is granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Audio permission is not granted", Toast.LENGTH_SHORT).show()
        }
    }
}