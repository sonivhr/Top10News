package com.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.util.addFragment
import com.newsapp.userexperience.auth.login.LoginFragment

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        if (supportFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName) == null) {
            addFragment(fragmentClass = LoginFragment::class.java,
                tag = LoginFragment::class.java.simpleName)
        }
    }

    override fun onBackPressed() {
        when (supportFragmentManager.fragments.size) {
            3 -> supportActionBar?.setTitle(R.string.title_headlines)
            2 -> supportActionBar?.hide()
            else -> supportActionBar?.hide()
        }
        super.onBackPressed()
    }
}