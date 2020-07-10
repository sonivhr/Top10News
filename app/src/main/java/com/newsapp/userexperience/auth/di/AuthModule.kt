package com.newsapp.userexperience.auth.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.newsapp.R
import com.newsapp.userexperience.auth.forgotpassword.ForgotPasswordViewModel
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

    @Provides
    @JvmStatic
    fun provideForgotPasswordViewModel(): ForgotPasswordViewModel = ForgotPasswordViewModel()

    @Provides
    @JvmStatic
    @Reusable
    fun provideGoogleSignInOptions(context: Context): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client))
            .requestEmail()
            .build()
}