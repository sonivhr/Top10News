package com.mvvmproject.di

import com.mvvmproject.rest.NewsApiInterface
import com.mvvmproject.rest.StoriesApiInterface
import dagger.Component
import javax.inject.Singleton

// Definition of the Application graph
@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun storiesApiInterface() : StoriesApiInterface

    fun newsApiInterface() : NewsApiInterface
}