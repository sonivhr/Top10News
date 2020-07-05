package com.mvvmproject.userexperience.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mvvmproject.helperclasses.DataLoadingState
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HeadlinesViewModel @Inject constructor(
    private val compositeDisposable: CompositeDisposable?,
    private val headlinesUseCase: HeadlinesUseCase) : ViewModel() {

    val articlesLiveData : LiveData<PagedList<Article>> =
        headlinesUseCase.headlinesPagedListLiveData

    override fun onCleared() {
        compositeDisposable?.clear()
        super.onCleared()
    }

    fun getDataLoadingState(): LiveData<DataLoadingState> {
        return Transformations.switchMap(
            headlinesUseCase.headlinesDataSourceFactory.headlinesDataSourceLiveData
        ) {
            it.dataLoadingStateLiveData
        }
    }

    fun retry() {
        headlinesUseCase.headlinesDataSourceFactory.headlinesPagedDataSource.retry()
    }

    fun isHeadlinesListEmpty() = articlesLiveData.value?.isEmpty() ?: true
}