package com.mvvmproject.userexperience.headlines.viewholders

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.databinding.ListItemArticleBinding
import com.mvvmproject.rest.responseobjects.Article
import com.mvvmproject.userexperience.listeners.OnArticleItemClickListener
import com.mvvmproject.util.loadOriginalImageWithGlide

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