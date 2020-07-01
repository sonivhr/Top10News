package com.mvvmproject.util

import android.util.Patterns
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

fun Long.covertToHumanReadableTime(): String {
    val timeFormat = "d MMM yyyy, hh:mm aaa"
    val date = Date(this * 1000)
    val format = SimpleDateFormat(timeFormat, Locale.getDefault())
    return format.format(date)
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

fun String?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this!!).matches()

fun String?.isValidPassword(): Pair<Boolean, ArrayList<String>?> {

    val missingCharsArray = ArrayList<String>()
    val password: String = this ?: ""

    if (8 > password.length || password.length > 16) {
        missingCharsArray.add("Password should be of 8-16 characters")
        return Pair(false, missingCharsArray)
    }

    val lowerCase = Pattern.compile(".*[a-z].*").matcher(password).matches()
    val upperCase = Pattern.compile(".*[A-Z].*").matcher(password).matches()
    val digits = Pattern.compile(".*\\d.*").matcher(password).matches()
    val specialCharacters =
        Pattern.compile(".*[!@#$%^&*()\\-+].*").matcher(password).matches()

    if (lowerCase && upperCase && digits && specialCharacters) {
        return Pair(true, null)
    }

    if (!lowerCase) {
        missingCharsArray.add("a lower case")
    }
    if (!upperCase) {
        missingCharsArray.add("an upper case")
    }
    if (!digits) {
        missingCharsArray.add("a digit")
    }
    if (!specialCharacters) {
        missingCharsArray.add("a special case")
    }
    return Pair(false, missingCharsArray)
}

fun String?.formatedAuthorName(): String? = this?.let { "By: $it" }