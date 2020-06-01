package com.mvvmproject.util

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.mvvmproject.R
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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

fun ArrayList<String>.convertListToCSV(): String {
    val stringBuilder = StringBuilder()
    if (this.size == 0) {
        return stringBuilder.toString()
    }
    if (this.size == 1) {
        stringBuilder.append("Missing: " + this[0])
        return stringBuilder.toString()
    }
    stringBuilder.append("Missing: " + this[0])
    for (i in 1..this.size - 1) {
        stringBuilder.append(", ${this[i]}")
    }
    return stringBuilder.toString()
}