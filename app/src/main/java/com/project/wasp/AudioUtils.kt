package com.project.wasp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AudioUtils (val context: Context){
    fun hasAudioPermission(context: Context): Boolean{
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity,Manifest.permission.RECORD_AUDIO)
            if(rationaleRequired){
                Toast.makeText(context,"Audio Permission is granted",Toast.LENGTH_SHORT).show()
                return true
            }
            else{
                Toast.makeText(context,"Permission is required. Enable it in Settings",Toast.LENGTH_SHORT).show()
                return false
            }
        }
        else{
            Toast.makeText(context,"Audio Permission is granted",Toast.LENGTH_SHORT).show()
            return true
        }
    }
}