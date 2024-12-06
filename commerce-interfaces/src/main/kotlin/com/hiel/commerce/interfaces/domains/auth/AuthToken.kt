package com.hiel.commerce.interfaces.domains.auth

import java.time.Duration

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        val ACCESS_TOKEN_EXPIRE_DURATION: Duration = Duration.ofMinutes(30)
        val REFRESH_TOKEN_EXPIRE_DURATION: Duration = Duration.ofDays(1)
    }
}
