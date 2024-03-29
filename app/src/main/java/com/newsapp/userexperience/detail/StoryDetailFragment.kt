package com.newsapp.userexperience.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.newsapp.R
import com.newsapp.util.ARGUMENT_URL
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wvDetail.settings.javaScriptEnabled = true
        wvDetail.loadUrl(arguments?.getString(ARGUMENT_URL))
        wvDetail.webChromeClient = MyWebChromeClient(progressBar)
        wvDetail.webViewClient = WebViewClient()
    }

    class MyWebChromeClient(private val progressBar: ProgressBar) : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                progressBar.visibility = View.GONE
            }
            progressBar.progress = newProgress
        }
    }
}