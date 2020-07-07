package com.newsapp.userexperience.listing

import com.newsapp.rest.response.StoryDetails

interface OnItemClickListener {
    fun onItemClick(position: Int, storyDetails: StoryDetails)
}