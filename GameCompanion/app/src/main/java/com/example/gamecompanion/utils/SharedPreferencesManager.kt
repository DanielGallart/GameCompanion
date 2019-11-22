package com.example.gamecompanion.utils

import android.content.Context

class SharedPreferencesManager{
   private val userPreferencesFileName = "userPreferences"
    private val usernameKey = "username"

    fun getUsername(context: Context):String?{
        val sharedPreferences = context.getSharedPreferences(userPreferencesFileName,Context.MODE_PRIVATE)
        return sharedPreferences.getString(usernameKey,null)
    }

    fun setUsername(context: Context, username: String?){
        val sharedPreferences = context.getSharedPreferences(userPreferencesFileName,Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(usernameKey,username).apply()
    }

    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(userPreferencesFileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}

