package com.project.wasp.ioutils

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.util.Log

class SpeakerUsageChecker(private val context: Context) {
    private fun isSpeakerInUse(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Check if music is active
        if (audioManager.isMusicActive) {
            Log.d("SpeakerUsageChecker","Speaker is in use")
            return true
        }

        // Check if the audio output is set to the speaker
        val outputDevices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in outputDevices) {
            if (device.type == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER) {
                Log.d("SpeakerUsageChecker","Speaker is in use")
                return true
            }
        }

        Log.d("SpeakerUsageChecker","Speaker is not in use")
        return false
    }

}