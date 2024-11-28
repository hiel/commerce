package com.hiel.commerce.interfaces.domains.auth

import com.hiel.commerce.common.domains.user.UserType
import com.hiel.commerce.common.utilities.EMPTY_STRING
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsImpl(
    val id: Long,
    val email: String,
    val userAccountId: String,
    val userType: UserType,
) : UserDetails {
    override fun getAuthorities() = emptyList<GrantedAuthority>()

    override fun getPassword() = EMPTY_STRING

    override fun getUsername() = EMPTY_STRING
}
