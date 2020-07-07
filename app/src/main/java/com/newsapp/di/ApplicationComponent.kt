package com.newsapp.di

import android.content.Context
import com.newsapp.MVVMApplication
import com.newsapp.userexperience.auth.LoginFragment
import com.newsapp.userexperience.headlines.HeadlinesFragment
import com.newsapp.userexperience.headlines.di.HeadlinesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Definition of the Application graph
@Singleton
@Component(modules = [NetworkModule::class,
    UserPrefModule::class,
    HeadlinesModule::class])
interface ApplicationComponent {

    // region for screen/userinterface injection
    fun inject(mvvmApplication: MVVMApplication)

    fun inject(loginFragment: LoginFragment)

    fun inject(headlinesFragment: HeadlinesFragment)
    // endregion

    // region ViewModels
    // endregion

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }
}