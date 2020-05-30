package com.mvvmproject

import android.app.Application
import com.facebook.stetho.Stetho
import com.mvvmproject.di.DaggerApplicationComponent
import com.mvvmproject.di.NetworkModule

class MVVMApplication: Application() {

    companion object {
        val appComponent = DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            initializeStetho()
        }
    }

    private fun initializeStetho() {

        Stetho.initializeWithDefaults(this)
    }
}