package com.mvvmproject.di

import android.content.Context
import com.mvvmproject.util.UserPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserPrefModule {
    @Singleton
    @Provides
    fun provideUserPreferenceManager(context: Context) =
        UserPreferenceManager(context)
}