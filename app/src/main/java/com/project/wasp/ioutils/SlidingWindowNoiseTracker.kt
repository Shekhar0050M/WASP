package com.project.wasp.ioutils

import android.content.Context
import android.content.SharedPreferences
import java.util.ArrayDeque
import java.util.Deque

class SlidingWindowNoiseTracker(private val context: Context, windowSizeMinutes: Int = 60) {
    private val windowSizeSeconds = windowSizeMinutes * 60
    private val window: Deque<Pair<Long, Double>> = ArrayDeque()
    private var totalSum = 0.0
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AudioUtilsPrefs", Context.MODE_PRIVATE)

    init {
        // Load previous noise levels from SharedPreferences
        loadNoiseLevels()
    }

    fun addNoise(timestamp: Long, noiseLevel: Double) {
        while (window.isNotEmpty() && (timestamp - window.peekFirst().first > windowSizeSeconds)) {
            val (oldTimestamp, oldNoise) = window.pollFirst()
            totalSum -= oldNoise
        }
        window.add(Pair(timestamp, noiseLevel))
        totalSum += noiseLevel
        saveNoiseLevels()
    }

    fun getAverageNoise(timestamp: Long): Double {
        return if (window.isNotEmpty()) totalSum / window.size else 0.0
    }

    private fun saveNoiseLevels() {
        val editor = sharedPreferences.edit()
        val noiseLevels = window.joinToString(",") { "${it.first}:${it.second}" }
        editor.putString("NoiseLevels", noiseLevels)
        editor.apply()
    }

    private fun loadNoiseLevels() {
        val noiseLevels = sharedPreferences.getString("NoiseLevels", "") ?: ""
        if (noiseLevels.isNotEmpty()) {
            noiseLevels.split(",").forEach {
                val (timestamp, noiseLevel) = it.split(":").map { value -> value.toDouble() }
                window.add(Pair(timestamp.toLong(), noiseLevel))
                totalSum += noiseLevel
            }
        }
    }
}
