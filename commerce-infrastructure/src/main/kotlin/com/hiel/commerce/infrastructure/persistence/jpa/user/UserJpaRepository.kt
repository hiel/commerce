package com.hiel.commerce.infrastructure.persistence.jpa.user

import com.hiel.commerce.common.domains.user.UserType
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findFirstById(id: Long): UserEntity?
    fun findFirstByEmailAndUserType(email: String, userType: UserType): UserEntity?
}
