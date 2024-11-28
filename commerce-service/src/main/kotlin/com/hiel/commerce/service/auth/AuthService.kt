package com.hiel.commerce.service.auth

import com.hiel.commerce.common.domains.user.UserStatus
import com.hiel.commerce.common.domains.user.UserType
import com.hiel.commerce.common.utilities.mail.MailTemplate
import com.hiel.commerce.common.utilities.mail.MailUtility
import com.hiel.commerce.infrastructure.cache.redis.signuptoken.SignupTokenCachePort
import com.hiel.commerce.infrastructure.persistence.jpa.user.UserPersistencePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val userPersistencePort: UserPersistencePort,
    private val signupTokenCachePort: SignupTokenCachePort,
    private val mailUtility: MailUtility,
): AuthUseCase {
    override fun signup(email: String, password: String, sellerName: String, certificationUrlHost: String) {
        userPersistencePort.checkDuplicated(
            email = email,
            userType = UserType.SELLER,
        )

        val user = userPersistencePort.saveUser(
            email = email,
            password = password,
            userType = UserType.SELLER,
            userStatus = UserStatus.NOT_CERTIFICATED,
        )

        val signupToken = signupTokenCachePort.save(user)
        mailUtility.sendMail(
            to = email,
            template = MailTemplate.SignupCertificate(MailTemplate.SignupCertificate.Params(
                certificationUrlHost = certificationUrlHost,
                token = signupToken.signupToken,
            )),
        )
    }

    @Transactional
    override fun certificateSignup(signupToken: String) {
        val token = signupTokenCachePort.getToken(signupToken)

        userPersistencePort.updateUserStatus(
            userId = token.userId,
            userStatus = UserStatus.AVAILABLE
        )

        signupTokenCachePort.delete(token)
    }
}
