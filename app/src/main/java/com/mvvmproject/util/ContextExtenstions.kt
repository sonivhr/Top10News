package com.mvvmproject.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvmproject.MVVMApplication
import com.mvvmproject.di.ApplicationComponent
import dagger.Lazy

fun Fragment.app(): MVVMApplication = context?.applicationContext as MVVMApplication

fun Fragment.appComponent(): ApplicationComponent = app().getAppComponent()

/**
 * Like [Fragment.viewModelProvider] for Fragment that want a [ViewModel] scoped to the Fragment.
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(creator: Lazy<T>? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this, BaseViewModelFactory { creator.get() }).get(T::class.java)
}

/**
 * Like [FragmentActivity.viewModelProvider] for FragmentActivity that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(creator: Lazy<T>? = null): T {
    return if (creator == null)
        ViewModelProvider(this).get(T::class.java)
    else
        ViewModelProvider(this, BaseViewModelFactory { creator.get() }).get(T::class.java)
}

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified T : ViewModel> Fragment.getActivityViewModel(creator: Lazy<T>? = null): T {
    return if (creator == null)
        ViewModelProvider(requireActivity()).get(T::class.java)
    else
        ViewModelProvider(requireActivity(), BaseViewModelFactory { creator.get() }).get(T::class.java)
}

/**
 * Like [AppCompatDialog.viewModelProvider] for AppCompatDialog that want a [ViewModel] scoped to the Activity.
 */
inline fun <reified T : ViewModel> AppCompatDialog.getActivityViewModel(creator: Lazy<T>? = null): T {
    return if (creator == null)
        ViewModelProvider(context.toFragmentActivity()).get(T::class.java)
    else
        ViewModelProvider(context.toFragmentActivity(), BaseViewModelFactory { creator.get() }).get(T::class.java)
}

fun Context.toActivity(): Activity = when {
    this is Activity -> this
    this is ContextWrapper -> this.baseContext.toActivity()
    else -> error("context is not a Activity")
}

fun Context.toFragmentActivity(): FragmentActivity =
    this.toActivity().let {
        if (it is FragmentActivity) {
            it
        } else {
            error("context is not a FragmentActivity")
        }
    }