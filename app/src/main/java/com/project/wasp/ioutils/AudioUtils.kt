package com.project.wasp.ioutils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import java.io.FileOutputStream
import java.util.Timer
import java.util.TimerTask

class AudioUtils (val context: Context){
    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    private val recordingIntervalMillis: Long = 24* 60 * 60 * 1000 // 1 day in milliseconds
    private val recordingDelayMillis: Long = 2 * 1000 // 45 seconds in milliseconds
    companion object {
        private const val RECORD_AUDIO_PERMISSION_CODE = 1001
        private const val SAMPLE_RATE = 44100
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

    @SuppressLint("DiscouragedApi")
    fun startRecording() {
        if (isRecordAudioPermissionGranted()) {
//            Toast.makeText(context, "Audio permission is granted", Toast.LENGTH_SHORT).show()
            val task1 = object: TimerTask(){
                override fun run() {
                    startRecordingTask()
                }
            }
            timer.scheduleAtFixedRate(task1,0,recordingDelayMillis)
            Timer().schedule(object: TimerTask(){
                override fun run() {
                    timer.cancel()
                    stopRecording()
                }
            },recordingIntervalMillis)
//
        } else {
//            Toast.makeText(context, "Audio permission is not granted", Toast.LENGTH_SHORT).show()
        }
    }
    fun stopRecording() {
        isRecording = false
        timer.cancel()
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
    @SuppressLint("MissingPermission")
    private fun startRecordingTask() {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        audioRecord?.startRecording()

        val data = ByteArray(bufferSize)
        val outputStream = FileOutputStream("/dev/null")

        val bytesRead = audioRecord?.read(data, 0, bufferSize) ?: 0
        if (bytesRead != AudioRecord.ERROR_INVALID_OPERATION) {
            outputStream.write(data, 0, bytesRead)
        }

        outputStream.close()
    }
}