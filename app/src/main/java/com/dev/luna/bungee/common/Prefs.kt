package com.dev.luna.bungee.common

import android.preference.PreferenceManager
import com.dev.luna.bungee.App

object Prefs {

    private const val TOKEN = "token"
    private const val REFRESH_TOKEN = "refresh_token"
    private const val USER_NAME = "user_name"
    private const val USER_ID = "user_id"

    val pref by lazy {
        PreferenceManager
                .getDefaultSharedPreferences(App.instance)
    }

    var token
        get() = pref.getString(TOKEN, null)
        set(value) = pref.edit()
                .putString(TOKEN, value)
                .apply()

    var refreshToken
        get() = pref.getString(REFRESH_TOKEN, null)
        set(value) = pref.edit()
                .putString(REFRESH_TOKEN, value)
                .apply()

    var userName
        get() = pref.getString(USER_NAME, null)
        set(value) = pref.edit()
                .putString(USER_NAME, value)
                .apply()

    var userId
        get() = pref.getLong(USER_ID, 0)
        set(value) = pref.edit()
                .putLong(USER_ID, value)
                .apply()


}