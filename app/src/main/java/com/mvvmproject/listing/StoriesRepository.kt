package com.mvvmproject.listing

import com.mvvmproject.MVVMApplication
import io.reactivex.Single

class StoriesRepository {

    fun getTopStories(): Single<List<Int>> =
        MVVMApplication.appComponent.storiesApiInterface().getTopStories()

    fun getStoryDetail(id: Int) =
        MVVMApplication.appComponent.storiesApiInterface().getStoryDetails(id)
}