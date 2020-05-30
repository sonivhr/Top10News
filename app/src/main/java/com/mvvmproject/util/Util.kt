package com.mvvmproject.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.covertToHumanReadableTime(): String {
    val timeFormat = "d MMM yyyy, hh:mm aaa"
    val date = Date(this * 1000)
    val format = SimpleDateFormat(timeFormat, Locale.getDefault())
    return format.format(date)
}