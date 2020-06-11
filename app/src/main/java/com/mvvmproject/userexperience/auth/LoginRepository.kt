package com.mvvmproject.userexperience.auth

import com.mvvmproject.rest.mock.MockLoginService

class LoginRepository {
    val mockLoginService = MockLoginService()

    fun validateUser(userName: String, password: String) =
        mockLoginService.validateUser(LoginRequest(userName, password))
}