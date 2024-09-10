package com.example.sample.Utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceManager {
    fun saveStringPreferences(context: Context, key: String, value: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveIntPreferences(context: Context, key: String, value: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun saveBooleanPreferences(context: Context, key: String, value: Boolean) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringPreferences(context: Context, key: String): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")!!
    }

    fun getIntPreferences(context: Context, key: String): Int {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }

    fun getBooleanPreferences(context: Context, key: String): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun clearPreference(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(AppConstants.SHARED, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}