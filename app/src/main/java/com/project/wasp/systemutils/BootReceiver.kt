package com.project.wasp.systemutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.project.wasp.ioutils.AudioUtils

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device booted")

            // Start your service or activity
            val serviceIntent = Intent(context, AudioUtils::class.java)
            context?.startService(serviceIntent)
        }
    }
}
