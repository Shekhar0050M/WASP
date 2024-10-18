package com.project.wasp.ioutils

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.util.Log

class SpeakerUsageChecker(private val context: Context) {
    public fun speakerStatus(): String {
        return "True"
    }
}