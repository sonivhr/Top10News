package com.newsapp.di

import android.content.Context
import com.newsapp.MVVMApplication
import com.newsapp.userexperience.auth.login.LoginFragment
import com.newsapp.userexperience.auth.di.AuthModule
import com.newsapp.userexperience.auth.forgotpassword.ForgotPasswordFragment
import com.newsapp.userexperience.auth.register.UserRegistrationFragment
import com.newsapp.userexperience.headlines.HeadlinesFragment
import com.newsapp.userexperience.headlines.di.HeadlinesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    UserPrefModule::class,
    AuthModule::class,
    HeadlinesModule::class])
interface ApplicationComponent {

    // region for screen/userinterface injection
    fun inject(mvvmApplication: MVVMApplication)

    fun inject(loginFragment: LoginFragment)

    fun inject(loginFragment: UserRegistrationFragment)

    fun inject(loginFragment: ForgotPasswordFragment)

    fun inject(headlinesFragment: HeadlinesFragment)
    // endregion

    // region ViewModels
    // endregion

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }
}