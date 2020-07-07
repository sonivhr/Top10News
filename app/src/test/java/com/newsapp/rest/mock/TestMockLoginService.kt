package com.newsapp.rest.mock

import com.newsapp.userexperience.auth.LoginRequest
import org.junit.Assert.*
import org.junit.Test

class TestMockLoginService {

    @Test
    fun testValidCredentials() {
        val loginRequest = LoginRequest("myemail@gmail.com",
        "Password@2020")

        val mockLoginService = MockLoginService()
        val loginResponse = mockLoginService.validateUser(loginRequest)

        assertEquals(VALID_CREDENTIAL_TOKEN, loginResponse.token)
        assertEquals(null, loginResponse.error)
        assertEquals(null, loginResponse.description)
        assertEquals(VALID_RESPONSE_CODE, loginResponse.responseCode)
    }

    @Test
    fun testInvalidCredentials() {
        val loginRequest = LoginRequest("myemail@gmail.com",
            "Password@2019")

        val mockLoginService = MockLoginService()
        val loginResponse = mockLoginService.validateUser(loginRequest)

        assertEquals(null, loginResponse.token)
        assertEquals(INVALID_CREDENTIALS_RESPONSE_ERROR, loginResponse.error)
        assertEquals(INVALID_CREDENTIALS_RESPONSE_DESCRIPTION, loginResponse.description)
        assertEquals(INVALID_CREDENTIALS_RESPONSE_CODE, loginResponse.responseCode)
    }
}