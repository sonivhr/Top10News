package com.newsapp.rest

import com.newsapp.rest.response.HeadlinesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "d34d3b599d3643288bc5ff974d81575c"
const val PAGE_SIZE = 20

interface NewsApiInterface {

    @GET("top-headlines?")
    fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("category") category: String = "business",
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ) : Single<HeadlinesResponse>
}