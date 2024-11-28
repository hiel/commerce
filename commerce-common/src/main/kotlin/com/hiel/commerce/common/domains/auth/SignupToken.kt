package com.hiel.commerce.common.domains.auth

data class SignupToken(
    val userId: Long,
    val signupToken: String,
)
