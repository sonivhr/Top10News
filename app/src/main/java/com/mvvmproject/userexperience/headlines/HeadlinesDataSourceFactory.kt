package com.mvvmproject.userexperience.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable

class HeadlinesDataSourceFactory(
    private val headlinesPagedDataSource: HeadlinesPagedDataSource
) : DataSource.Factory<Int, Article>() {

    val headlinesDataSourceLiveData = MutableLiveData<HeadlinesPagedDataSource>()

    override fun create(): DataSource<Int, Article> {
        headlinesDataSourceLiveData.postValue(headlinesPagedDataSource)
        return headlinesPagedDataSource
    }
}