package com.newsapp.userexperience.auth.forgotpassword

import com.newsapp.helperclasses.Event
import com.newsapp.userexperience.auth.login.LoginBaseViewModel

class ForgotPasswordViewModel: LoginBaseViewModel() {

    fun validateEmail() {
        if (isValidEmail()) {
            validCredentialEventLiveData.value = Event(true)
        }
    }
}