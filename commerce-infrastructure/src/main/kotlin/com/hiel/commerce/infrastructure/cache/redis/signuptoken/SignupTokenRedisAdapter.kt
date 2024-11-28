package com.hiel.commerce.infrastructure.cache.redis.signuptoken

import com.hiel.commerce.common.domains.auth.SignupToken
import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.exceptions.ResultCode
import com.hiel.commerce.common.exceptions.ServiceException
import org.springframework.stereotype.Component

@Component
class SignupTokenRedisAdapter(
    private val signupTokenRedisRepository: SignupTokenRedisRepository,
): SignupTokenCachePort {
    override fun save(user: User) = signupTokenRedisRepository.save(SignupTokenRedisEntity.build(user)).toSignupToken()

    override fun delete(signupToken: SignupToken) = signupTokenRedisRepository.delete(SignupTokenRedisEntity.build(signupToken))

    override fun getToken(signupToken: String) = signupTokenRedisRepository.findBySignupToken(signupToken = signupToken)?.toSignupToken()
        ?: throw ServiceException(ResultCode.Auth.EXPIRED_TOKEN)
}
