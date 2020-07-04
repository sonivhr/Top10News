package com.mvvmproject.userexperience.headlines

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mvvmproject.rest.PAGE_SIZE

class HeadlinesUseCase(
    private val headlinesDataSourceFactory: HeadlinesDataSourceFactory
) {

    private val headlinesPagedListConfig = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    val headlinesPagedListLiveData by lazy {
        LivePagedListBuilder(headlinesDataSourceFactory, headlinesPagedListConfig).build()
    }
}