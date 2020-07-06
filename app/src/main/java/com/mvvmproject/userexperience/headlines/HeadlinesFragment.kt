package com.mvvmproject.userexperience.headlines

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mvvmproject.R
import com.mvvmproject.helperclasses.DataLoadingState
import com.mvvmproject.helperclasses.NoInternetException
import com.mvvmproject.rest.responseobjects.Article
import com.mvvmproject.userexperience.detail.StoryDetailFragment
import com.mvvmproject.userexperience.listeners.OnArticleItemClickListener
import com.mvvmproject.util.*
import dagger.Lazy
import kotlinx.android.synthetic.main.layout_listing.*
import javax.inject.Inject

class HeadlinesFragment : Fragment(R.layout.layout_listing), OnArticleItemClickListener {

    @Inject
    lateinit var headlinesViewModelCreator: Lazy<HeadlinesViewModel>
    private lateinit var headlinesViewModel: HeadlinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        headlinesViewModel = getViewModel(headlinesViewModelCreator)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setTitle(R.string.title_headlines)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeHeadlinesData()
        observeDataLoadingState()
    }

    private fun setClickListeners() {
        tvNoResult.setOnClickListener {
            headlinesViewModel.retry()
        }
    }

    private fun observeHeadlinesData() {
        val headlinesAdapter = HeadlinesAdapter(this) { headlinesViewModel.retry() }
        rvStoriesList.adapter = headlinesAdapter
        headlinesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            articlesPagedList ->
            rvStoriesList.visibility = View.VISIBLE
            headlinesAdapter.submitList(articlesPagedList)
        })
    }

    private fun observeDataLoadingState() {
        headlinesViewModel.getDataLoadingState().observe(viewLifecycleOwner, Observer {
            dataLoadingState ->
            progressBar.visibility = if (dataLoadingState is DataLoadingState.Loading
                && headlinesViewModel.isHeadlinesListEmpty()) VISIBLE else GONE
            handleNoResultText(dataLoadingState)
            (rvStoriesList.adapter as HeadlinesAdapter).setDataLoadingState(dataLoadingState)
        })
    }

    private fun handleNoResultText(dataLoadingState: DataLoadingState) {
        if (dataLoadingState is DataLoadingState.Error && headlinesViewModel.isHeadlinesListEmpty())  {
            tvNoResult.visibility = VISIBLE
            if (dataLoadingState.throwable is NoInternetException) {
                tvNoResult.text = getText(R.string.message_no_connectivity)
            } else {
                tvNoResult.text = getText(R.string.message_issue_while_loading_data)
            }
        } else {
            tvNoResult.visibility = GONE
        }
    }

    override fun onArticleItemClick(position: Int, article: Article) {
        if (article.url == null) {
            requireActivity().showSnackBar(getString(R.string.message_article_detail_unavailable))
            return
        }
        val bundle = Bundle()
        bundle.putString(ARGUMENT_URL, article.url)
        requireActivity().addFragmentWithBackStack(fragmentClass = StoryDetailFragment::class.java,
            args = bundle,
            tag = StoryDetailFragment::class.java.simpleName)
    }
}