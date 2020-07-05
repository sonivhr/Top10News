package com.mvvmproject.di

import android.content.Context
import com.mvvmproject.helperclasses.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserPrefModule {
    @Reusable
    @Provides
    fun provideUserPreferenceManager(context: Context) =
        UserPreferenceManager(context)
}