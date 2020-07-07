package com.newsapp.userexperience.headlines

import com.newsapp.rest.NewsApiInterface

class HeadlinesRepository (
    private val remoteDataSource: NewsApiInterface
) {

    fun getTopHeadlines(page: Int, pageSize: Int) =
        remoteDataSource.getTopHeadlines(page = page, pageSize = pageSize)
}