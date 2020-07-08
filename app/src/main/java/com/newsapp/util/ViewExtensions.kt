package com.newsapp.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.newsapp.R

fun ImageView.loadOriginalImageWithGlide(urlPath: String?) {
    Glide.with(this.context)
        .load(urlPath)
        .error(R.drawable.ic_image_not_available)
        .into(this)
}

fun TextInputEditText.onDoneButtonClick(inputMethodManager: InputMethodManager, callback: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
            callback.invoke()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
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