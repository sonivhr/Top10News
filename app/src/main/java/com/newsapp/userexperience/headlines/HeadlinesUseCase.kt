package com.newsapp.userexperience.headlines

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.newsapp.rest.PAGE_SIZE

class HeadlinesUseCase(
    val headlinesDataSourceFactory: HeadlinesDataSourceFactory
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