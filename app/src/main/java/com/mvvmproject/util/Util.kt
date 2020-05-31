package com.mvvmproject.util

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.mvvmproject.R
import java.text.SimpleDateFormat
import java.util.*


fun Long.covertToHumanReadableTime(): String {
    val timeFormat = "d MMM yyyy, hh:mm aaa"
    val date = Date(this * 1000)
    val format = SimpleDateFormat(timeFormat, Locale.getDefault())
    return format.format(date)
}

fun Activity.showSnackBar(message: String) {
    val snackbar = Snackbar.make(this.findViewById(R.id.mainScreenCoordinatorLayout),
        message, Snackbar.LENGTH_LONG)
    snackbar.show()
}