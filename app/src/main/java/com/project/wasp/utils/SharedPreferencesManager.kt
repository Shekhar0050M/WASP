package com.project.wasp.utils

import android.content.Context

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveValue(key: String, value: String) {
//        Log.d("SharedPreferencesManager", "Saving value: $value with key: $key")
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String, defaultValue: String): String? {
//        Log.d("SharedPreferencesManager", "Retrieved value: ${sharedPreferences.getString(key,defaultValue)} for key: $key")
        return sharedPreferences.getString(key, defaultValue)
    }
}