package com.newsapp.rest.response

import com.newsapp.rest.responseobjects.Article

data class HeadlinesResponse(
    val articles: List<Article>,
    val status : String?,
    val totalResults : Int?
)