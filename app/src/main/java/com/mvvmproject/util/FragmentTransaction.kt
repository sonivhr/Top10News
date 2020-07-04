package com.mvvmproject.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mvvmproject.R

const val ARGUMENT_URL = "url"

fun FragmentActivity.addFragment(fragmentClass: Class<out Fragment>,
                                 args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.beginTransaction()
        .add(R.id.container, fragmentClass, args, tag)
        .commit()
}

fun FragmentActivity.addFragmentWithBackStack(fragmentClass: Class<out Fragment>,
                                              args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.beginTransaction()
        .add(R.id.container, fragmentClass, args, tag)
        .addToBackStack(null)
        .commit()
}

fun FragmentActivity.replaceFragmentWithBackStack(fragmentClass: Class<out Fragment>,
                                              args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragmentClass, args, tag)
        .addToBackStack(null)
        .commit()
}