package com.newsapp.userexperience.headlines.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.databinding.ListItemFooterBinding
import com.newsapp.helperclasses.DataLoadingState
import com.newsapp.helperclasses.NoInternetException

class FooterViewHolder(private val listItemFooterBinding: ListItemFooterBinding) :
    RecyclerView.ViewHolder(listItemFooterBinding.root) {

    fun bindView(dataLoadingState: DataLoadingState, retryLoadingArticles: () -> Unit) {
        listItemFooterBinding.dataLoadingState = dataLoadingState
        if (dataLoadingState is DataLoadingState.Error) {
            listItemFooterBinding.tvErrorMessage.text =
                if (dataLoadingState.throwable is NoInternetException) {
                    listItemFooterBinding.root.context.getString(R.string.message_no_connectivity)
                } else {
                    listItemFooterBinding.root.context.getString(R.string.message_issue_while_loading_data)
                }
        }
        listItemFooterBinding.tvErrorMessage.setOnClickListener { retryLoadingArticles() }
    }
}