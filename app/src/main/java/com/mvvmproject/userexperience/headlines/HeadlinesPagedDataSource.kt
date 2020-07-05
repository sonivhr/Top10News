package com.mvvmproject.userexperience.headlines

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mvvmproject.helperclasses.DataLoadingState
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

private const val TAG = "HeadlinesDataSource"
class HeadlinesPagedDataSource(
    private val headlinesRepository: HeadlinesRepository,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    private var retryAction: Action? = null
    val dataLoadingStateLiveData: MutableLiveData<DataLoadingState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {
        updateDataLoadingState(DataLoadingState.Loading)
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = 1, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        setRetry(null)
                        updateDataLoadingState(DataLoadingState.Idle)
                        callback.onResult(headlinesResponse.articles, null, 2)
                    },
                    { throwable ->
                        updateDataLoadingState(DataLoadingState.Error(throwable))
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        updateDataLoadingState(DataLoadingState.Loading)
        compositeDisposable.add(
            headlinesRepository.getTopHeadlines(page = params.key, pageSize = params.requestedLoadSize)
                .subscribe(
                    { headlinesResponse ->
                        setRetry(null)
                        updateDataLoadingState(DataLoadingState.Idle)
                        callback.onResult(headlinesResponse.articles, params.key + 1)
                    },
                    { throwable ->
                        updateDataLoadingState(DataLoadingState.Error(throwable))
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun updateDataLoadingState(dataLoadingState: DataLoadingState) {
        dataLoadingStateLiveData.postValue(dataLoadingState)
    }
}