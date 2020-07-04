package com.mvvmproject.userexperience.headlines

import androidx.paging.PageKeyedDataSource
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

private const val TAG = "HeadlinesDataSource"
class HeadlinesPagedDataSource(
    private val headlinesRepository: HeadlinesRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    private var retryAction: Action? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        println("CompositeDisposable: $compositeDisposable")
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = 1, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        setRetry(null)
                        callback.onResult(headlinesResponse.articles, null, 2)
                    },
                    { throwable ->
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = params.key, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        setRetry(null)
                        callback.onResult(headlinesResponse.articles, params.key + 1)
                    },
                    { throwable ->
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) { }

    private fun setRetry(action: Action?) {
        retryAction = action
    }

    fun retry() {
        if(retryAction != null) {
            compositeDisposable.add(Completable.fromAction(retryAction)
                .subscribe())
        }
    }
}