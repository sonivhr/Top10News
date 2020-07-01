package com.mvvmproject.userexperience.headlines

import com.mvvmproject.rest.NewsApiInterface

class HeadlinesRepository (
    private val remoteDataSource: NewsApiInterface
) {

    fun getTopHeadlines(page: Int, pageSize: Int) =
        remoteDataSource.getTopHeadlines(page = page, pageSize = pageSize)
}