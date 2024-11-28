package com.hiel.commerce.infrastructure.persistence.jpa.user

import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.domains.user.UserStatus
import com.hiel.commerce.common.domains.user.UserType

interface UserPersistencePort {
    fun saveUser(email: String, password: String, userType: UserType, userStatus: UserStatus): User
    fun checkDuplicated(email: String, userType: UserType)
    fun updateUserStatus(userId: Long, userStatus: UserStatus)
}
