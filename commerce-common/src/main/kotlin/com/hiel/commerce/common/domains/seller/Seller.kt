package com.hiel.commerce.common.domains.seller

import com.hiel.commerce.common.domains.user.User

data class Seller(
    val name: String,
    val user: User,
) {
    companion object {
        const val NAME_MINIMUM_LENGTH = 2
    }
}
