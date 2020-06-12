package com.mvvmproject.userexperience.headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.databinding.ListItemArticleBinding
import com.mvvmproject.rest.responseobjects.Article
import com.mvvmproject.userexperience.listeners.OnArticleItemClickListener
import com.mvvmproject.util.loadOriginalImageWithGlide

class HeadlinesAdapter(
    private val onArticleItemClickListener: OnArticleItemClickListener
) : PagedListAdapter<Article, HeadlinesAdapter.ArticleViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItemArticleBinding : ListItemArticleBinding = DataBindingUtil.inflate(layoutInflater,
        R.layout.list_item_article, parent,false)
        return ArticleViewHolder(listItemArticleBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindView(getItem(position), position, onArticleItemClickListener)
    }

    class ArticleViewHolder(private val listItemArticleBinding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(listItemArticleBinding.root) {

        private val cardViewArticle =
            listItemArticleBinding.root.findViewById<CardView>(R.id.cvArticle)

        fun bindView(article: Article?, position: Int,
                     onArticleItemClickListener: OnArticleItemClickListener) {
            listItemArticleBinding.article = article
            listItemArticleBinding.ivArticle.loadOriginalImageWithGlide(article?.urlToImage)
            cardViewArticle.setOnClickListener {
                onArticleItemClickListener.onArticleItemClick(position, article!!)
            }
        }
    }

    companion object {
        val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}