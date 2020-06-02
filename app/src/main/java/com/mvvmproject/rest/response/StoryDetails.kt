package com.mvvmproject.rest.response

data class StoryDetails(
    val id: Int,
    val by: String,
    val time: Long,
    val title: String,
    val url: String?
)