package com.hiel.commerce.infrastructure.persistence.jpa.user

import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.domains.user.UserStatus
import com.hiel.commerce.common.domains.user.UserType
import com.hiel.commerce.infrastructure.persistence.jpa.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.envers.Audited

@Audited
@Entity
@Table(name = "users", schema = "commerce")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "email", updatable = false, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    var encryptPassword: String,

    @Column(name = "user_type", updatable = false, nullable = false)
    @Enumerated(value = EnumType.STRING)
    val userType: UserType,

    @Column(name = "user_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var userStatus: UserStatus,
): BaseEntity() {
    fun toUser() = User(
        id = id,
        email = email,
        encryptPassword = encryptPassword,
        userType = userType,
        userStatus = userStatus,
    )
}
