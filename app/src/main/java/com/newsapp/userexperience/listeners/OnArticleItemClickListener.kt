package com.newsapp.userexperience.listeners

import com.newsapp.rest.responseobjects.Article

interface OnArticleItemClickListener {
    fun onArticleItemClick(position: Int, article: Article)
}