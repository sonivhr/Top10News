package com.mvvmproject.userexperience.headlines.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.databinding.ListItemFooterBinding
import com.mvvmproject.helperclasses.DataLoadingState

class FooterViewHolder(private val listItemFooterBinding: ListItemFooterBinding):
    RecyclerView.ViewHolder(listItemFooterBinding.root) {

    fun bindView(dataLoadingState: DataLoadingState, retryLoadingArticles: () -> Unit) {
        listItemFooterBinding.dataLoadingState = dataLoadingState
        listItemFooterBinding.tvErrorMessage.setOnClickListener { retryLoadingArticles() }
    }
}