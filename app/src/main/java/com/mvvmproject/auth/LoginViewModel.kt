package com.mvvmproject.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private val loginRepository = LoginRepository()

    val loginResponseLiveData = MutableLiveData<LoginResponse>()

    fun validateUser(username: String, password: String) {
        loginResponseLiveData.value = loginRepository.validateUser(username, password)
    }

}