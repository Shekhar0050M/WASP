package com.project.wasp.ioutils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import java.text.DecimalFormat
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
    private val startInterval: Long = 5 * 1000 // 30 seconds in milliseconds
    private val stopInterval: Long = 2 * 1000 // 45 seconds in milliseconds
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
           val startTask = object: TimerTask() {
                override fun run(){
                    startRecordingTask()
                }
           }
            val stopTask = object: TimerTask() {
                override fun run(){
                    stopRecordingTask()
                }
            }
            timer.scheduleAtFixedRate(startTask, 0, startInterval + stopInterval)
            timer.scheduleAtFixedRate(stopTask, startInterval, startInterval + stopInterval)
//
        } else {
//            Toast.makeText(context, "Audio permission is not granted", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording() {
        if(isRecording) {
            isRecording = false
            timer.cancel()
            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null
        }
    }
    fun stopRecordingTask() {
        if(isRecording) {
            isRecording = false
//            timer.cancel()
            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null
        }
//        println("Recording is stopped")
    }
    @SuppressLint("MissingPermission")
    private fun startRecordingTask() {
        isRecording = true
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        audioRecord?.startRecording()

    }

    fun calculateAmplitude(): String {
        val decimalFormat = DecimalFormat("#.##")
        if(!isRecording) {
            return "0.0"
        }
        val buffer = ShortArray(bufferSize)
        val readSize = audioRecord?.read(buffer, 0, bufferSize, AudioRecord.READ_NON_BLOCKING)
        var amplitude = 0.0

            if (readSize != null && readSize > 0) {
                for (i in 0 until readSize) {
                    amplitude += buffer[i] * buffer[i]
                }
                amplitude /= readSize.toDouble()
                amplitude = Math.sqrt(amplitude)
            }


        return decimalFormat.format(amplitude).toString()
    }


}