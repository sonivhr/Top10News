package com.mvvmproject.userexperience.headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mvvmproject.R
import com.mvvmproject.rest.responseobjects.Article
import com.mvvmproject.userexperience.detail.StoryDetailFragment
import com.mvvmproject.userexperience.listeners.OnArticleItemClickListener
import com.mvvmproject.util.ARGUMENT_URL
import com.mvvmproject.util.addFragmentWithBackStack
import com.mvvmproject.util.showSnackBar
import kotlinx.android.synthetic.main.layout_listing.*

class HeadlinesFragment : Fragment(), OnArticleItemClickListener {

    private lateinit var headlinesViewModel: HeadlinesViewModel

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

        headlinesViewModel = ViewModelProvider(this).get(HeadlinesViewModel::class.java)
        startDataLoading()
    }

    private fun startDataLoading() {
        val headlinesAdapter = HeadlinesAdapter(this)
        rvStoriesList.adapter = headlinesAdapter
        headlinesViewModel.articlesLiveData.observe(viewLifecycleOwner, Observer {
            articlesPagedList -> headlinesAdapter.submitList(articlesPagedList)
        })
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