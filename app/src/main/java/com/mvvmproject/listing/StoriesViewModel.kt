package com.mvvmproject.listing

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvmproject.rest.response.StoryDetails
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

private const val MAX_STORY_DETAIL = 20

class StoriesViewModel : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val storiesRepository = StoriesRepository()
    private var noOfLoadedPages = 0
    private val compositeDisposable = CompositeDisposable()

    private lateinit var listTopStories: List<Int>
    private val liveDataTopStoriesThrowable = MutableLiveData<Throwable>()

    val liveDataStoriesDetail = MutableLiveData<List<StoryDetails>>()
    val liveDataStoriesDetailThrowable = MutableLiveData<Throwable>()

    private var isLoadingStories = false

    init {
        startLoadingTopStories()
    }

    private fun startLoadingTopStories() {
        compositeDisposable.add(
            storiesRepository.getTopStories()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { topStories ->
                        listTopStories = topStories
                        loadStoriesDetail()
                    },
                    { throwable ->
                        Log.e(TAG, "Got error on loading topStories: ${throwable.message}")
                        liveDataTopStoriesThrowable.postValue(throwable)
                    })
        )
    }

    private fun loadStoriesDetail() {
        val startIndex = noOfLoadedPages * MAX_STORY_DETAIL
        val endIndex = startIndex + MAX_STORY_DETAIL
        compositeDisposable.add(
            Observable.fromIterable(listTopStories.subList(startIndex, endIndex))
                .concatMap { id -> storiesRepository.getStoryDetail(id).toObservable() }
                .toList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { isLoadingStories = true }
                .subscribe(
                    { storiesDetails ->
                        noOfLoadedPages += 1
                        liveDataStoriesDetail.postValue(storiesDetails)
                        isLoadingStories = false
                    },
                    { throwable ->
                        Log.e(TAG, "Got an error while get story details: ${throwable.message}")
                        liveDataStoriesDetailThrowable.postValue(throwable)
                        isLoadingStories = false
                    }
                )
        )
    }

    fun loadNextSetOfStories() {
        if (!isLoadingStories) {
            loadStoriesDetail()
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}