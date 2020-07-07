package com.mvvmproject.userexperience.headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.databinding.ListItemArticleBinding
import com.mvvmproject.databinding.ListItemFooterBinding
import com.mvvmproject.helperclasses.DataLoadingState
import com.mvvmproject.rest.responseobjects.Article
import com.mvvmproject.userexperience.headlines.viewholders.ArticleViewHolder
import com.mvvmproject.userexperience.headlines.viewholders.FooterViewHolder
import com.mvvmproject.userexperience.listeners.OnArticleItemClickListener

private const val VIEW_TYPE_DATA = 1
private const val VIEW_TYPE_FOOTER = 2
class HeadlinesAdapter(
    private val onArticleItemClickListener: OnArticleItemClickListener,
    private val retryLoadingArticles: () -> Unit
) : PagedListAdapter<Article, RecyclerView.ViewHolder>(ARTICLE_DIFF_CALLBACK) {

    private var dataLoadingState: DataLoadingState = DataLoadingState.Loading

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder: RecyclerView.ViewHolder
        viewHolder = if (viewType == VIEW_TYPE_DATA) {
            val listItemArticleBinding : ListItemArticleBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.list_item_article, parent,false)
            ArticleViewHolder(listItemArticleBinding)
        } else {
            val listItemFooterBinding : ListItemFooterBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.list_item_footer, parent, false)
            FooterViewHolder(listItemFooterBinding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_FOOTER -> (holder as FooterViewHolder).bindView(dataLoadingState, retryLoadingArticles)
            else -> (holder as ArticleViewHolder).bindView(getItem(position), position, onArticleItemClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) VIEW_TYPE_DATA else VIEW_TYPE_FOOTER
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooterView()) 1 else 0
    }

    private fun hasFooterView(): Boolean {
        return super.getItemCount() != 0 &&
                (dataLoadingState is DataLoadingState.Loading || dataLoadingState is DataLoadingState.Error)
    }

    fun setDataLoadingState(dataLoadingState: DataLoadingState) {
        this.dataLoadingState = dataLoadingState
        notifyItemChanged(super.getItemCount())
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