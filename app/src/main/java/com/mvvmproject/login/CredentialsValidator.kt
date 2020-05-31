package com.mvvmproject.login

import android.util.Patterns
import java.util.regex.Pattern

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this!!).matches()

fun String.isValidPassword(): Pair<Boolean, ArrayList<String>?> {
    val missingCharsArray = ArrayList<String>()

    if (8 > this.length && this.length > 16) {
        missingCharsArray.add("Password should be of 8-16 characters")
        return Pair(false, missingCharsArray)
    }

    val lowerCase = Pattern.compile(".*[a-z].*").matcher(this).matches()
    val upperCase = Pattern.compile(".*[A-Z].*").matcher(this).matches()
    val digits = Pattern.compile(".*\\d.*").matcher(this).matches()
    val specialCharacters =
        Pattern.compile(".*[!@#$%^&*()\\-+].*").matcher(this).matches()

    if (lowerCase && upperCase && digits && specialCharacters) {
        return Pair(true, null)
    }

    if (!lowerCase) {
        missingCharsArray.add("lower case")
    }
    if (!upperCase) {
        missingCharsArray.add("upper case")
    }
    if (!digits) {
        missingCharsArray.add("upper case")
    }
    if (!specialCharacters) {
        missingCharsArray.add("special case")
    }
    return Pair(false, missingCharsArray)
}