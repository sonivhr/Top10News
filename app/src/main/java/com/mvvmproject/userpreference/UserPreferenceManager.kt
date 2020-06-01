package com.mvvmproject.userpreference

import android.content.Context

private const val SHARED_PREFERENCE_NAME = "MY_SHAREDPREFERENCES"
const val PREF_IS_DARK_APP_THEME = "IS_DARK_APP_THEME"

class UserPreferenceManager(context: Context) {

    private var sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun putInt(prefKey: String, prefValue: Int) {
        with(sharedPreferences.edit()) {
            putInt(prefKey, prefValue)
            commit()
        }
    }

    fun getInt(prefKey: String, defaultValue: Int = 0) =
        sharedPreferences.getInt(prefKey, defaultValue)

    fun putBoolean(prefKey: String, prefValue: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(prefKey, prefValue)
            commit()
        }
    }

    fun getBoolean(prefKey: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefKey, defaultValue)

    fun putString(prefKey: String, prefValue: String) {
        with(sharedPreferences.edit()) {
            putString(prefKey, prefValue)
            commit()
        }
    }

    fun getString(prefKey: String, defaultValue: String = "") =
        sharedPreferences.getString(prefKey, defaultValue)
}