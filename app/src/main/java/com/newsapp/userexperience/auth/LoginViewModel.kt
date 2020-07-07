package com.newsapp.userexperience.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.R
import com.newsapp.helperclasses.Event
import com.newsapp.util.convertListToCSV
import com.newsapp.util.isValidEmail
import com.newsapp.util.isValidPassword

class LoginViewModel: ViewModel() {

    private val TAG = this.javaClass.simpleName
    var username: String? = "myemail@gmail.com"
    val usernameErrorLiveData = MutableLiveData<Int?>()
    var password: String? = "Password@2020"
    val passwordErrorLiveData = MutableLiveData<String?>()

    private val loginRepository = LoginRepository()

    val loginResponseEventLiveData = MutableLiveData<Event<LoginResponse>>()

    fun validateUser() {
        if (isValidEmail() && isValidPassword()) {
            loginResponseEventLiveData.value =
                Event(
                    loginRepository.validateUser(
                        username!!,
                        password!!
                    )
                )
        }
    }

    private fun isValidEmail(): Boolean {
        if (!username.isValidEmail()) {
            usernameErrorLiveData.value = R.string.invalid_email
            return false
        }
        usernameErrorLiveData.value = null
        return true
    }

    private fun isValidPassword(): Boolean {
        val passwordValidation = password?.isValidPassword()
        if (passwordValidation?.first == true) {
            passwordErrorLiveData.value = null
            return true
        }
        passwordErrorLiveData.value = passwordValidation?.second?.convertListToCSV() ?: ""
        return false
    }
}