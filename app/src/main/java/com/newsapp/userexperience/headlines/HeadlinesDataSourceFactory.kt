package com.newsapp.userexperience.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.newsapp.rest.responseobjects.Article

class HeadlinesDataSourceFactory(
    val headlinesPagedDataSource: HeadlinesPagedDataSource
) : DataSource.Factory<Int, Article>() {

    val headlinesDataSourceLiveData = MutableLiveData<HeadlinesPagedDataSource>()

    override fun create(): DataSource<Int, Article> {
        headlinesDataSourceLiveData.postValue(headlinesPagedDataSource)
        return headlinesPagedDataSource
    }
}