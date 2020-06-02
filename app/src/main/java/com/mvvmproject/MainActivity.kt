package com.mvvmproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvmproject.util.addFragment
import com.mvvmproject.auth.LoginFragment

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        addFragment(fragmentClass = LoginFragment::class.java, tag = LoginFragment::class.java.simpleName)
    }

    override fun onBackPressed() {
        when (supportFragmentManager.fragments.size) {
            3 -> supportActionBar?.setTitle(R.string.title_top_stories)
            2 -> supportActionBar?.hide()
            else -> supportActionBar?.hide()
        }
        super.onBackPressed()
    }
}