package com.mvvmproject.userexperience.headlines

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mvvmproject.MVVMApplication
import com.mvvmproject.rest.PAGE_SIZE
import io.reactivex.disposables.CompositeDisposable

class HeadlinesRepo(private val compositeDisposable: CompositeDisposable) {

    private val newsApiInterface = MVVMApplication.appComponent.newsApiInterface()

    private val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

    val headlinesDataSourceFactory by lazy {
        HeadlinesDataSourceFactory(newsApiInterface, compositeDisposable)
    }

    val headlinesPagedListLiveData by lazy {
        LivePagedListBuilder(headlinesDataSourceFactory, pagedListConfig).build()
    }
}