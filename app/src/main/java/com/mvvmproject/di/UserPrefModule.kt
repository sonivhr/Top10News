package com.mvvmproject.di

import android.content.Context
import com.mvvmproject.util.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
object UserPrefModule {
    @Reusable
    @Provides
    fun provideUserPreferenceManager(context: Context) = UserPreferenceManager(context)
}