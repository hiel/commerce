package com.hiel.commerce.infrastructure.cache.redis.signuptoken

import com.hiel.commerce.common.domains.auth.SignupToken
import com.hiel.commerce.common.domains.user.User

interface SignupTokenCachePort {
    fun save(user: User): SignupToken
    fun delete(signupToken: SignupToken)
    fun getToken(signupToken: String): SignupToken
}
