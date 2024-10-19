package com.project.wasp.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.wasp.R
import com.project.wasp.systemutils.AppForegroundService
import com.project.wasp.utils.SharedPreferencesManager

class GraphicalInfoFragment: Fragment() {

    private lateinit var amplitudeMicTextView: TextView
    private lateinit var averageAmplitudeMicTextView: TextView
    private lateinit var speakerUsageCheckerTextView: TextView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000L // Update every second (1000 milliseconds)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.graphical_information, container, false)
        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Initialize AudioUtils TextView
        amplitudeMicTextView = view.findViewById(R.id.audioUtils)
        averageAmplitudeMicTextView = view.findViewById(R.id.audioAverage)
        speakerUsageCheckerTextView= view.findViewById(R.id.speakerUsage)


        val intent = Intent(activity, AppForegroundService::class.java)
        activity?.startService(intent)
        // Inflate the layout for this fragment
        return view
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            // Retrieve amplitudeString from SharedPreferences
            val amplitudeString = sharedPreferencesManager.getValue("amplitudeText", "Audio amplitude is not available")
            amplitudeMicTextView.text = amplitudeString

            val averageAmplitudeString = sharedPreferencesManager.getValue("averageAmplitudeText", "Average noise level is not available")
            averageAmplitudeMicTextView.text = averageAmplitudeString

            val speakerUsageCheckerString = sharedPreferencesManager.getValue("speakerUsageText", "Checking speaker......")
            speakerUsageCheckerTextView.text = speakerUsageCheckerString
            // Schedule the next update
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // You can initialize UI components and set listeners here
        // Handler to update amplitudeTextView and save amplitudeString in SharedPreferences
        handler.post(updateRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Stop the handler when the fragment's view is destroyed
        handler.removeCallbacks(updateRunnable)
    }
}