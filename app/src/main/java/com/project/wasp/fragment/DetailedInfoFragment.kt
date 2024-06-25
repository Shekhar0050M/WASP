package com.project.wasp.fragment

import android.content.Context
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

class DetailedInfoFragment: Fragment() {

    private lateinit var amplitudeMicTextView: TextView
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

        // Initialize AudioUtils TextView
        amplitudeMicTextView = view.findViewById(R.id.audioUtils)

        val intent = Intent(activity, AppForegroundService::class.java)
        activity?.startService(intent)
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
            // Retrieve amplitudeString from SharedPreferences
            val amplitudeString = sharedPreferencesManager.getValue("amplitudeText", "")
            amplitudeMicTextView.text = amplitudeString

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