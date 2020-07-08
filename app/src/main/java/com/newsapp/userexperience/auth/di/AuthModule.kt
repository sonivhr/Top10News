package com.newsapp.userexperience.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.newsapp.userexperience.auth.login.LoginViewModel
import com.newsapp.userexperience.auth.register.UserRegistrationViewModel
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthModule {
    @Provides
    @Reusable
    @JvmStatic
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @JvmStatic
    fun provideLoginViewModel(): LoginViewModel = LoginViewModel()

    @Provides
    @JvmStatic
    fun provideUserRegistrationViewModel(): UserRegistrationViewModel = UserRegistrationViewModel()
}