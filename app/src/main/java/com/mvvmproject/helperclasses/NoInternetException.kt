package com.mvvmproject.helperclasses

import java.io.IOException

class NoInternetException: IOException() {
    override val message: String
        get() = "Internet connectivity is not available."
}