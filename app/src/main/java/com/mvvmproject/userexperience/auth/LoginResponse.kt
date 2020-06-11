package com.mvvmproject.userexperience.auth

class LoginResponse(
    val token: String? = null,
    val error: String? = null,
    val description: String? = null,
    val responseCode: Int = 200
)