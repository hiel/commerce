package com.hiel.commerce.infrastructure.persistence.jpa.user

import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.domains.user.UserStatus
import com.hiel.commerce.common.domains.user.UserType
import com.hiel.commerce.common.exceptions.ResultCode
import com.hiel.commerce.common.exceptions.ServiceException
import org.springframework.stereotype.Component

@Component
class UserJpaAdapter(
    private val userJpaRepository: UserJpaRepository,
): UserPersistencePort {
    override fun saveUser(email: String, password: String, userType: UserType, userStatus: UserStatus): User {
        return userJpaRepository.save(UserEntity(
            email = email,
            encryptPassword = password,
            userType = userType,
            userStatus = userStatus,
        )).toUser()
    }

    override fun checkDuplicated(email: String, userType: UserType) {
        userJpaRepository.findFirstByEmailAndUserType(email = email, userType = userType)
            ?: throw ServiceException(ResultCode.Auth.DUPLICATED_EMAIL)
    }

    override fun updateUserStatus(userId: Long, userStatus: UserStatus) {
        val user = userJpaRepository.findFirstById(userId)
            ?: throw ServiceException(ResultCode.Auth.NOT_EXIST_USER)
        user.userStatus = userStatus
    }
}
