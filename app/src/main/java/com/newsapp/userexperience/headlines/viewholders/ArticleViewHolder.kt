package com.newsapp.userexperience.headlines.viewholders

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.ListItemArticleBinding
import com.newsapp.rest.responseobjects.Article
import com.newsapp.userexperience.listeners.OnArticleItemClickListener
import com.newsapp.util.loadOriginalImageWithGlide

class ArticleViewHolder(private val listItemArticleBinding: ListItemArticleBinding) :
    RecyclerView.ViewHolder(listItemArticleBinding.root) {

    private val cardViewArticle =
        listItemArticleBinding.root.findViewById<CardView>(R.id.cvArticle)

    fun bindView(
        article: Article?, position: Int,
        onArticleItemClickListener: OnArticleItemClickListener
    ) {
        listItemArticleBinding.article = article
        listItemArticleBinding.ivArticle.loadOriginalImageWithGlide(article?.urlToImage)
        cardViewArticle.setOnClickListener {
            onArticleItemClickListener.onArticleItemClick(position, article!!)
        }
    }
}