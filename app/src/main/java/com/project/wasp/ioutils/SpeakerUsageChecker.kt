package com.project.wasp.ioutils

import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter

class SpeakerUsageChecker(private val context: Context) {
        private var isSpeakerInUse = false

        // BroadcastReceiver to track audio output changes
        private val audioOutputReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                isSpeakerInUse = checkIfSpeakerInUse(audioManager)
            }
        }

        init {
            // Register receiver to track any changes in audio output
            val intentFilter = IntentFilter(AudioManager.ACTION_HEADSET_PLUG)
            context.registerReceiver(audioOutputReceiver, intentFilter)
        }

        // Check if the speaker is currently in use
        private fun checkIfSpeakerInUse(audioManager: AudioManager): Boolean {
            // Checking the output mode: speaker or other
            return audioManager.isSpeakerphoneOn || !audioManager.isWiredHeadsetOn
        }

        // Function to check if speaker is in use at the moment
        fun isSpeakerActive(): Boolean {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return checkIfSpeakerInUse(audioManager)
        }

        // Unregister the receiver when not needed
        fun unregisterReceiver() {
            context.unregisterReceiver(audioOutputReceiver)
            Log.d("SpeakerUsageChecker", "Unregistered SpeakerUsageChecker")
        }


}