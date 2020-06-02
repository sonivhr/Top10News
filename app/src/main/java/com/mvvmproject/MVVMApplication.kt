package com.mvvmproject

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.mvvmproject.di.DaggerApplicationComponent
import com.mvvmproject.di.NetworkModule
import com.mvvmproject.util.PREF_IS_DARK_APP_THEME
import com.mvvmproject.util.UserPreferenceManager

class MVVMApplication: Application() {

    companion object {
        val appComponent = DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initializeTheme()
        if (BuildConfig.DEBUG) {
            initializeStetho()
        }
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeTheme() {
        val userPreferenceManager =
            UserPreferenceManager(this)
        if (userPreferenceManager.getBoolean(PREF_IS_DARK_APP_THEME)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}