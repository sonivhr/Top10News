package com.mvvmproject.userexperience.listing

import com.mvvmproject.rest.StoriesApiInterface
import io.reactivex.Single
import javax.inject.Inject

class StoriesRepository {

    @Inject
    lateinit var storiesApiInterface: StoriesApiInterface

    fun getTopStories(): Single<ArrayList<Int>> = storiesApiInterface.getTopStories()

    fun getStoryDetail(id: Int) = storiesApiInterface.getStoryDetails(id)
}