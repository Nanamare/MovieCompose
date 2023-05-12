package com.nanamare.data.source.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalStore @Inject constructor(context: Application) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(KEY_LOCAL_STORE, Context.MODE_PRIVATE)

    private fun get(key: String, defaultValue: Boolean): Boolean =
        preferences.getBoolean(key, defaultValue)

    private fun set(key: String, value: Boolean) = preferences.edit {
        putBoolean(key, value)
    }

    fun get(key: String, defaultValue: Int): Int = preferences.getInt(key, defaultValue)

    fun set(key: String, value: Int) = preferences.edit {
        putInt(key, value)
    }

    private fun get(key: String, defaultValue: String?): String? =
        preferences.getString(key, defaultValue)

    fun set(key: String, value: String?) = preferences.edit(commit = true) {
        putString(key, value)
    }

    companion object {
        const val KEY_LOCAL_STORE = "preferences.movie"
    }

}
