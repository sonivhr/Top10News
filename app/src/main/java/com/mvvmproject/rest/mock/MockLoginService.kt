package com.mvvmproject.rest.mock

import com.mvvmproject.userexperience.auth.LoginRequest
import com.mvvmproject.userexperience.auth.LoginResponse

private const val VALID_USERNAME = "myemail@gmail.com"
private const val VALID_PASSWORD = "Password@2020"

const val VALID_CREDENTIAL_TOKEN = "VwvYXBpXC9"
const val VALID_RESPONSE_CODE = 200

const val INVALID_CREDENTIALS_RESPONSE_ERROR = "invalid_credentials"
const val INVALID_CREDENTIALS_RESPONSE_DESCRIPTION = "Email address and password is not a " +
        "valid combination."
const val INVALID_CREDENTIALS_RESPONSE_CODE = 401

const val BAD_REQUEST_RESPONSE_ERROR = "bad_request"
const val BAD_REQUEST_RESPONSE_DESCRIPTION = "Network communication error."
const val BAD_REQUEST_RESPONSE_CODE = 400

class MockLoginService() {

    fun validateUser(loginRequest: LoginRequest): LoginResponse {

        return if (loginRequest.username == VALID_USERNAME &&
                loginRequest.password == VALID_PASSWORD) {
            LoginResponse(token = VALID_CREDENTIAL_TOKEN, responseCode = VALID_RESPONSE_CODE)
        } else if (loginRequest.username != VALID_USERNAME || loginRequest.password != VALID_PASSWORD) {
            LoginResponse(error = INVALID_CREDENTIALS_RESPONSE_ERROR,
                description = INVALID_CREDENTIALS_RESPONSE_DESCRIPTION,
                responseCode = INVALID_CREDENTIALS_RESPONSE_CODE)
        } else {
            LoginResponse(error = BAD_REQUEST_RESPONSE_ERROR,
                description = BAD_REQUEST_RESPONSE_DESCRIPTION,
                responseCode = BAD_REQUEST_RESPONSE_CODE)
        }
    }
}