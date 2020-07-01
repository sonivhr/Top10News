package com.mvvmproject.userexperience.headlines

import androidx.paging.PageKeyedDataSource
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable

private const val TAG = "HeadlinesDataSource"
class HeadlinesPagedDataSource(
    private val headlinesRepository: HeadlinesRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        println("CompositeDisposable: $compositeDisposable")
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = 1, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        callback.onResult(headlinesResponse.articles, null, 2)
                    },
                    { throwable ->
//                        Log.e(TAG, "loadInitial: ${(throwable as )}")
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = params.key, pageSize = params.requestedLoadSize)
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