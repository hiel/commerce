package com.hiel.commerce.common.domains.customer

import com.hiel.commerce.common.domains.user.User

data class Customer(
    val name: String,
    val user: User,
) {
    companion object {
        const val NAME_MINIMUM_LENGTH = 2
    }
}
