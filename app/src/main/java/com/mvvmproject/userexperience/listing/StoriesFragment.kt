package com.mvvmproject.userexperience.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvmproject.R
import com.mvvmproject.userexperience.detail.StoryDetailFragment
import com.mvvmproject.util.addFragmentWithBackStack
import com.mvvmproject.rest.response.StoryDetails
import com.mvvmproject.util.ARGUMENT_URL
import com.mvvmproject.util.showSnackBar
import kotlinx.android.synthetic.main.layout_listing.*

class StoriesFragment : Fragment(), OnItemClickListener {

    private val TAG = this.javaClass.simpleName
    private lateinit var storiesViewModel: StoriesViewModel
    private var storyViewAdapter: StoryViewAdapter? = null

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setTitle(R.string.title_headlines)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.layout_listing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvStoriesList.also {
            it.setHasFixedSize(false)
            it.addOnScrollListener(rvOnScrollListener)
        }

        storiesViewModel = ViewModelProvider(this).get(StoriesViewModel::class.java)
        observeStoriesViewModel()

        swipeRefreshStories.setOnRefreshListener {
            storiesViewModel.refreshStories()
        }
    }

    private fun observeStoriesViewModel() {
        storiesViewModel.liveDataStoriesDetail.observe(viewLifecycleOwner, Observer {
            storiesDetails ->
            rvStoriesList.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            if (storyViewAdapter == null || swipeRefreshStories.isRefreshing) {
                storyViewAdapter = StoryViewAdapter(storiesDetails, this)
                rvStoriesList.adapter = storyViewAdapter
            } else {
                storyViewAdapter?.addStories(storiesDetails)
            }
            stopRefreshingIcon()
        })

        storiesViewModel.liveDataStoriesDetailThrowable.observe(viewLifecycleOwner, Observer {
                throwable ->
            requireActivity().showSnackBar(getString(R.string.message_error_loading_stories_details))
            stopRefreshingIcon()
        })

        storiesViewModel.liveDataTopStoriesThrowable.observe(viewLifecycleOwner, Observer {
                throwable ->
            requireActivity().showSnackBar(getString(R.string.message_error_loading_top_stories))
            stopRefreshingIcon()
        })
    }

    override fun onItemClick(position: Int, storyDetails: StoryDetails) {
        if (storyDetails.url == null) {
            requireActivity().showSnackBar(getString(R.string.message_article_detail_unavailable))
            return
        }
        val bundle = Bundle()
        bundle.putString(ARGUMENT_URL, storyDetails.url)
        requireActivity().addFragmentWithBackStack(fragmentClass = StoryDetailFragment::class.java,
            args = bundle,
            tag = StoryDetailFragment::class.java.simpleName)
    }

    private val rvOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItem =
                (rvStoriesList.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            storyViewAdapter?.let {
                // If user is scrolling the list and only 10 items are remaining in the list then
                // it's good time to fetch more stories
                if (it.itemCount - lastVisibleItem <= 10 && storiesViewModel.loadNextSetOfStories()) {
                    requireActivity().showSnackBar(getString(R.string.message_loading_more_stories))
                }
            }
        }
    }

    private fun stopRefreshingIcon() {
        if (swipeRefreshStories.isRefreshing) {
            swipeRefreshStories.isRefreshing = false
        }
    }
}