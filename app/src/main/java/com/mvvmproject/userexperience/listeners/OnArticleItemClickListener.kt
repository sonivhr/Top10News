package com.mvvmproject.userexperience.listeners

import com.mvvmproject.rest.responseobjects.Article

interface OnArticleItemClickListener {
    fun onArticleItemClick(position: Int, article: Article)
}