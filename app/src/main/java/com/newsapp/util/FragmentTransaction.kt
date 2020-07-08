package com.newsapp.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.newsapp.R

const val ARGUMENT_URL = "url"

fun FragmentActivity.addFragment(fragmentClass: Class<out Fragment>,
                                 args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.beginTransaction()
        .add(R.id.container, fragmentClass, args, tag)
        .commit()
}

fun FragmentActivity.clearBackStackAndAddFragment(fragmentClass: Class<out Fragment>,
                                                  args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    addFragment(fragmentClass, args, tag)
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

fun FragmentActivity.replaceFragmentWithoutBackStack(fragmentClass: Class<out Fragment>,
                                                  args: Bundle? = null, tag: String? = null) {
    this.supportFragmentManager.beginTransaction()
        .replace(R.id.container, fragmentClass, args, tag)
        .commit()
}