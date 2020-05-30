package com.mvvmproject.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mvvmproject.R
import com.mvvmproject.listing.ARGUMENT_URL
import kotlinx.android.synthetic.main.layout_detail.*

class StoryDetailFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setTitle(R.string.title_story_detail)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.layout_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wvDetail.loadUrl(arguments?.getString(ARGUMENT_URL))
    }
}