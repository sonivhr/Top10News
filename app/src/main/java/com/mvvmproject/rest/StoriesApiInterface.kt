package com.mvvmproject.rest

import com.mvvmproject.rest.response.StoryDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StoriesApiInterface {

    @GET("topstories.json")
    fun getTopStories(): Single<ArrayList<Int>>

    @GET("item/{id}.json")
    fun getStoryDetails(@Path("id") id: Int): Single<StoryDetails>
}