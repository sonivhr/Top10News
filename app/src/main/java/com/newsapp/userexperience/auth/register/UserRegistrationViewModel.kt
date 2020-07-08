package com.newsapp.userexperience.auth.register

import androidx.lifecycle.MutableLiveData
import com.newsapp.R
import com.newsapp.helperclasses.Event
import com.newsapp.userexperience.auth.login.LoginBaseViewModel

class UserRegistrationViewModel: LoginBaseViewModel() {
    var confirmPassword: String = ""
    val confirmPasswordErrorLiveData = MutableLiveData<Int?>()

    fun registerAndLogin() {
        if (isValidEmail() && isValidPassword() && isValidConfirmPassword()) {
            validCredentialEventLiveData.value = Event(true)
        }
    }

    private fun isValidConfirmPassword(): Boolean {
        val isValidConfirmPassword = password.equals(confirmPassword)
        if (!isValidConfirmPassword) {
            confirmPasswordErrorLiveData.value = R.string.message_confirm_password
        }
        return isValidConfirmPassword
    }
}