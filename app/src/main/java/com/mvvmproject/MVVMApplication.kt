package com.mvvmproject

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.mvvmproject.di.ApplicationComponent
import com.mvvmproject.di.DaggerApplicationComponent
import com.mvvmproject.util.PREF_IS_DARK_APP_THEME
import com.mvvmproject.util.UserPreferenceManager
import javax.inject.Inject

class MVVMApplication: MultiDexApplication() {

    private var appComponent: ApplicationComponent? = null

    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager

    override fun onCreate() {
        super.onCreate()
        getAppComponent().inject(this)
        initializeTheme()
        if (BuildConfig.DEBUG) {
            initializeStetho()
        }
    }

    fun getAppComponent(): ApplicationComponent {
        if (appComponent == null) {
            synchronized(this) {
                if (appComponent == null) {
                    appComponent = DaggerApplicationComponent.factory().create(this)
                }
            }
        }
        return requireNotNull(appComponent)
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initializeTheme() {
        if (userPreferenceManager.getBoolean(PREF_IS_DARK_APP_THEME)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}