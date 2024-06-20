package com.project.wasp.systemutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Boot completed received")
            val serviceIntent = Intent(context, ForegroundService::class.java)
            context?.startForegroundService(serviceIntent)
            Log.d("BootReceiver", "ForegroundService started on boot")
        }
    }
}
