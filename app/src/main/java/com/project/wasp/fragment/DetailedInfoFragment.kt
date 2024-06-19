package com.project.wasp.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.wasp.R
import com.project.wasp.ioutils.AudioUtils
import com.project.wasp.utils.SharedPreferencesManager

class DetailedInfoFragment: Fragment() {

    private lateinit var audioRecorder: AudioUtils
    private lateinit var amplitudeTextView: TextView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000L // Update every second (1000 milliseconds)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.detailed_information, container, false)
        // Initialize SharedPreferencesManager
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        // Initialize AudioUtils and TextView
        audioRecorder = AudioUtils(requireContext())
        amplitudeTextView = view.findViewById(R.id.audioUtils)

        // Start recording
        audioRecorder.startRecording()
        // Inflate the layout for this fragment
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // You can initialize UI components and set listeners here
        // Handler to update amplitudeTextView and save amplitudeString in SharedPreferences
        handler.post(updateRunnable)
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            val amplitude = audioRecorder.calculateAmplitude()
            val amplitudeString = buildString {
                append(getString(R.string.audio_amplitude_text))
                append(": ")
                append(amplitude)
            }

            // Update amplitudeTextView
            amplitudeTextView.text = amplitudeString

            Log.d("SharedPreferencesManager", "Saving value: $amplitudeString with key: amplitudeText")
            // Save amplitudeString in SharedPreferences
            sharedPreferencesManager.saveValue("amplitudeText", amplitudeString)

            // Schedule the next update
            handler.postDelayed(this, updateInterval)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Stop the handler when the fragment's view is destroyed
        handler.removeCallbacks(updateRunnable)
    }
}