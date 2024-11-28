package com.hiel.commerce.service.auth

interface AuthUseCase {
    fun signup(email: String, password: String, sellerName: String, certificationUrlHost: String)
    fun certificateSignup(signupToken: String)
}
