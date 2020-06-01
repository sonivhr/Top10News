package com.mvvmproject.di

import android.content.Context
import com.mvvmproject.userpreference.UserPreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserPrefModule {
    @Singleton
    @Provides
    fun provideUserPreferenceManager(context: Context) = UserPreferenceManager(context)
}