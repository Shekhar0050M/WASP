package com.project.wasp.ioutils

import java.util.ArrayDeque
import java.util.Deque

    class SlidingWindowNoiseTracker(windowSizeMinutes: Int = 10) {
        private val windowSizeSeconds: Long = windowSizeMinutes * 60L
        private val window: Deque<Pair<Long, Int>> = ArrayDeque()
        private var totalSum: Long = 0

        // Add new noise data and remove old entries beyond the sliding window duration
        fun addNoise(noiseLevel: Int) {
            val timestamp = System.currentTimeMillis() / 1000  // Current time in seconds

            // Remove old data points outside the sliding window
            while (window.isNotEmpty() && (timestamp - window.peekFirst().first > windowSizeSeconds)) {
                val oldData = window.pollFirst()
                totalSum -= oldData.second
            }

            // Add the new noise data
            window.addLast(Pair(timestamp, noiseLevel))
            totalSum += noiseLevel
        }

        // Calculate the average noise
        fun getAverageNoise(): Double {
            return if (window.isNotEmpty()) totalSum.toDouble() / window.size else 0.0
        }
    }