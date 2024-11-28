package com.hiel.commerce.interfaces.utilities

import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.domains.user.UserType
import com.hiel.commerce.common.exceptions.ResultCode
import com.hiel.commerce.common.exceptions.ServiceException
import com.hiel.commerce.common.utilities.getNowKst
import com.hiel.commerce.common.utilities.toDate
import com.hiel.commerce.interfaces.domains.auth.AuthToken
import com.hiel.commerce.interfaces.domains.auth.TokenType
import com.hiel.commerce.interfaces.domains.auth.UserDetailsImpl
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import java.time.Duration
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtTokenUtility(
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
) {
    companion object {
        const val CLAIM_KEY_TOKEN_TYPE = "tokenType"
    }

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateAuthToken(user: User): AuthToken {
        val claims = mapOf(
            "id" to user.id,
            "email" to user.email,
            "userType" to user.userType,
        )
        return AuthToken(
            accessToken = generateToken(
                claims = claims,
                tokenType = TokenType.ACCESS_TOKEN,
                expireDuration = AuthToken.ACCESS_TOKEN_EXPIRE_DURATION,
            ),
            refreshToken = generateToken(
                claims = claims,
                tokenType = TokenType.REFRESH_TOKEN,
                expireDuration = AuthToken.REFRESH_TOKEN_EXPIRE_DURATION,
            ),
        )
    }

    fun generateToken(claims: Map<String, *>, tokenType: TokenType, expireDuration: Duration): String {
        return Jwts.builder()
            .claims(claims)
            .claim(CLAIM_KEY_TOKEN_TYPE, tokenType.name)
            .issuedAt(getNowKst().toDate())
            .expiration(getNowKst().plus(expireDuration).toDate())
            .signWith(key)
            .compact()
    }

    fun parseToken(token: String, tokenType: TokenType): UserDetailsImpl {
        try {
            val parsed = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload

            if (tokenType.name != parsed[CLAIM_KEY_TOKEN_TYPE]) {
                throw MalformedJwtException("Invalid token type (${parsed[CLAIM_KEY_TOKEN_TYPE]})")
            }

            return UserDetailsImpl(
                id = (parsed["id"] as Int).toLong(),
                email = parsed["email"] as String,
                userAccountId = parsed["userAccountId"] as String,
                userType = UserType.valueOf(parsed["userType"] as String),
            )

        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ServiceException(ResultCode.Auth.EXPIRED_ACCESS_TOKEN)
                is MalformedJwtException,
                is SignatureException -> throw ServiceException(ResultCode.Auth.INVALID_TOKEN)
                else -> throw ServiceException(ResultCode.Auth.AUTHENTICATION_FAIL)
            }
        }
    }
}
