package com.mvvmproject.listing

import com.mvvmproject.rest.response.StoryDetails

interface OnItemClickListener {
    fun onItemClick(position: Int, storyDetails: StoryDetails)
}