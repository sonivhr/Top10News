package com.mvvmproject.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvmproject.R
import com.mvvmproject.util.convertListToCSV
import com.mvvmproject.util.isValidEmail
import com.mvvmproject.util.isValidPassword

class LoginViewModel: ViewModel() {

    private val TAG = this.javaClass.simpleName
    var username: String? = null
    val usernameErrorLiveData = MutableLiveData<Int?>()
    var password: String? = null
    val passwordErrorLiveData = MutableLiveData<String?>()

    private val loginRepository = LoginRepository()

    val loginResponseLiveData = MutableLiveData<LoginResponse>()

    fun validateUser() {
        if (isValidEmail() && isValidPassword()) {
            loginResponseLiveData.value = loginRepository.validateUser(username!!, password!!)
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