package com.mvvmproject.auth

import com.mvvmproject.mock.MockLoginService

class LoginRepository {
    val mockLoginService = MockLoginService()

    fun validateUser(userName: String, password: String) =
        mockLoginService.validateUser(LoginRequest(userName, password))
}