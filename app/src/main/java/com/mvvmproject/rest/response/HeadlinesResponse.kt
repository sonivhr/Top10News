package com.mvvmproject.rest.response

import com.mvvmproject.rest.responseobjects.Article

data class HeadlinesResponse(
    val article: List<Article>,
    val status : String?,
    val totalResults : Int?
)