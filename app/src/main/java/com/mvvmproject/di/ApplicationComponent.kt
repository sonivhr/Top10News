package com.mvvmproject.di

import android.content.Context
import com.mvvmproject.MVVMApplication
import com.mvvmproject.userexperience.auth.LoginFragment
import com.mvvmproject.userexperience.headlines.HeadlinesFragment
import com.mvvmproject.userexperience.headlines.HeadlinesViewModel
import com.mvvmproject.userexperience.headlines.di.HeadlinesModule
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