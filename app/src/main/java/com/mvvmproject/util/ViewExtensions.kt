package com.mvvmproject.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mvvmproject.R

fun ImageView.loadOriginalImageWithGlide(urlPath: String?) {
    Glide.with(this.context)
        .load(urlPath)
        .error(R.drawable.ic_image_not_available)
        .into(this)
}

fun Activity.showSnackBar(message: String) {
    val snackbar = Snackbar.make(
        this.findViewById(R.id.mainScreenCoordinatorLayout),
        message, Snackbar.LENGTH_LONG
    )
    snackbar.show()
}

fun Context.isInternetConnected(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}