package com.newsapp.userexperience.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.R
import com.newsapp.helperclasses.Event
import com.newsapp.util.convertListToCSV
import com.newsapp.util.isValidEmail
import com.newsapp.util.isValidPassword

open class LoginBaseViewModel: ViewModel() {
    var email: String = ""
    val emailErrorLiveData = MutableLiveData<Int?>()
    var password: String = ""
    val passwordErrorLiveData = MutableLiveData<String?>()
    val validCredentialEventLiveData = MutableLiveData<Event<Boolean>>()

    protected fun isValidEmail(): Boolean {
        if (!email.isValidEmail()) {
            emailErrorLiveData.value = R.string.invalid_email
            return false
        }
        emailErrorLiveData.value = null
        return true
    }

    protected fun isValidPassword(): Boolean {
        val passwordValidation = password.isValidPassword()
        if (passwordValidation.first) {
            passwordErrorLiveData.value = null
            return true
        }
        passwordErrorLiveData.value = passwordValidation.second?.convertListToCSV() ?: ""
        return false
    }
}