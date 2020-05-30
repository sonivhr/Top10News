package com.mvvmproject.listing

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
import com.mvvmproject.detail.StoryDetailFragment
import com.mvvmproject.fragmentutil.replaceFragment
import com.mvvmproject.rest.response.StoryDetails
import kotlinx.android.synthetic.main.layout_listing.*

const val ARGUMENT_URL = "url"
class StoriesFragment : Fragment(), OnItemClickListener {

    private val TAG = this.javaClass.simpleName
    private lateinit var storiesViewModel: StoriesViewModel
    private lateinit var rvLayoutManager: RecyclerView.LayoutManager
    private var storyViewAdapter: StoryViewAdapter? = null

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setTitle(R.string.title_top_stories)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.layout_listing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLayoutManager = LinearLayoutManager(activity)
        rvStoriesList.also {
            it.setHasFixedSize(false)
            it.layoutManager = rvLayoutManager
            it.addOnScrollListener(rvOnScrollListener)
        }

        storiesViewModel = ViewModelProvider(this).get(StoriesViewModel::class.java)
        observeStoriesViewModel()
    }

    private fun observeStoriesViewModel() {
        storiesViewModel.liveDataStoriesDetail.observe(viewLifecycleOwner, Observer {
            storiesDetails ->
            rvStoriesList.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            if (storyViewAdapter == null) {
                storyViewAdapter = StoryViewAdapter(requireContext(), storiesDetails, this)
                rvStoriesList.adapter = storyViewAdapter
            } else {
                storyViewAdapter?.addStories(storiesDetails)
            }
        })
    }

    override fun onItemClick(position: Int, storyDetails: StoryDetails) {
        val bundle = Bundle()
        bundle.putString(ARGUMENT_URL, storyDetails.url)
        activity?.replaceFragment(fragmentClass = StoryDetailFragment::class.java,
            args = bundle,
            tag = StoryDetailFragment::class.java.simpleName)
    }

    private val rvOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItem =
                (rvLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            storyViewAdapter?.let {
                val percentScrolled = (lastVisibleItem * 100) / it.itemCount
                // If list is scrolled more than 50% load next step of stories
                if (percentScrolled > 50) {
                    storiesViewModel.loadNextSetOfStories()
                }
            }
        }
    }
}