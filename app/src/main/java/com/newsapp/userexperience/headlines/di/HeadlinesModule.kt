package com.newsapp.userexperience.headlines.di

import com.newsapp.rest.NewsApiInterface
import com.newsapp.userexperience.headlines.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named
import javax.inject.Singleton

@Module
object HeadlinesModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideHeadlinesRepository(remoteDataSource: NewsApiInterface): HeadlinesRepository =
        HeadlinesRepository(remoteDataSource)

    @Provides
    @Reusable
    @Named("HeadlinesCompositeDisposable")
    @JvmStatic
    fun provideCompositeDisposable() : CompositeDisposable = CompositeDisposable()

    @Provides
    @JvmStatic
    fun provideHeadlinesPagedDataSource(
        headlinesRepository: HeadlinesRepository,
        @Named("HeadlinesCompositeDisposable") compositeDisposable: CompositeDisposable): HeadlinesPagedDataSource =
        HeadlinesPagedDataSource(headlinesRepository, compositeDisposable)

    @Provides
    @JvmStatic
    fun provideHeadlinesDataSourceFactory(headlinesPagedDataSource: HeadlinesPagedDataSource):
            HeadlinesDataSourceFactory = HeadlinesDataSourceFactory(headlinesPagedDataSource)

    @Provides
    @JvmStatic
    fun provideHeadlinesUseCase(headlinesDataSourceFactory: HeadlinesDataSourceFactory): HeadlinesUseCase =
        HeadlinesUseCase(headlinesDataSourceFactory)

    @Provides
    @JvmStatic
    fun provideHeadlinesViewModel(
        @Named("HeadlinesCompositeDisposable") compositeDisposable: CompositeDisposable,
        headlinesUseCase: HeadlinesUseCase): HeadlinesViewModel = HeadlinesViewModel(compositeDisposable, headlinesUseCase)
}