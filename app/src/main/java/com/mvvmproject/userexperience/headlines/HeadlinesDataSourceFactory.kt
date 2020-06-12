package com.mvvmproject.userexperience.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mvvmproject.rest.NewsApiInterface
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable

class HeadlinesDataSourceFactory(
    private val newsApiInterface: NewsApiInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Article>() {

    val headlinesDataSourceLiveData = MutableLiveData<HeadlinesDataSource>()

    override fun create(): DataSource<Int, Article> {
        val articleDataSource = HeadlinesDataSource(newsApiInterface, compositeDisposable)
        headlinesDataSourceLiveData.postValue(articleDataSource)
        return articleDataSource
    }
}