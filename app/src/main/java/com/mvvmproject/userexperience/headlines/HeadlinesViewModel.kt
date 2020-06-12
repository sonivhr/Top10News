package com.mvvmproject.userexperience.headlines

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mvvmproject.rest.responseobjects.Article
import io.reactivex.disposables.CompositeDisposable

class HeadlinesViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val headlinesRepo = HeadlinesRepo(compositeDisposable)
    val articlesLiveData : LiveData<PagedList<Article>>

    init {
        articlesLiveData = headlinesRepo.headlinesPagedListLiveData
    }
}