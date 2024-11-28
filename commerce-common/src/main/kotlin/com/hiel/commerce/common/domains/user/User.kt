package com.hiel.commerce.common.domains.user

data class User(
    val id: Long,
    val email: String,
    val encryptPassword: String,
    val userType: UserType,
    val userStatus: UserStatus,
) {
    companion object {
        const val PASSWORD_MINIMUM_LENGTH = 8
    }
}
