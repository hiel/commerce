package com.hiel.commerce.infrastructure.cache.redis.signuptoken

import com.hiel.commerce.common.domains.auth.SignupToken
import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.infrastructure.cache.redis.BaseRedisEntity
import com.hiel.commerce.common.utilities.minuteToSecond
import java.util.UUID
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash("SignupToken")
class SignupTokenRedisEntity(
    @Id
    var userId: Long,
    @Indexed
    var signupToken: String,
) : BaseRedisEntity {
    @TimeToLive
    override fun getTtlSecond() = 10.minuteToSecond()

    companion object {
        fun build(signupToken: SignupToken) = SignupTokenRedisEntity(
            userId = signupToken.userId,
            signupToken = signupToken.signupToken,
        )

        fun build(user: User) = SignupTokenRedisEntity(
            userId = user.id,
            signupToken = UUID.randomUUID().toString(),
        )
    }

    fun toSignupToken() = SignupToken(
        userId = userId,
        signupToken = signupToken,
    )
}
