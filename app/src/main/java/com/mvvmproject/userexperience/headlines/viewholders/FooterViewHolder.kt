package com.mvvmproject.userexperience.headlines.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.databinding.ListItemFooterBinding
import com.mvvmproject.helperclasses.DataLoadingState
import com.mvvmproject.helperclasses.NoInternetException

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