package com.newsapp.userexperience.auth.login

import com.newsapp.helperclasses.Event

class LoginViewModel: LoginBaseViewModel() {
    fun validateUser() {
        if (isValidEmail() && isValidPassword()) {
            validCredentialEventLiveData.value = Event(true)
        }
    }
}