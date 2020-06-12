package com.mvvmproject.userexperience.headlines

import androidx.paging.PageKeyedDataSource
import com.mvvmproject.rest.NewsApiInterface
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable

class HeadlinesDataSource(
    private val newsApiInterface: NewsApiInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        compositeDisposable.add(
            newsApiInterface.getTopHeadlines(page = 1, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        callback.onResult(headlinesResponse.articles, null, 2)
                    },
                    { throwable ->
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        compositeDisposable.add(
            newsApiInterface.getTopHeadlines(page = params.key, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        callback.onResult(headlinesResponse.articles, params.key + 1)
                    },
                    { throwable ->
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) { }
}