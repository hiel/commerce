package com.hiel.commerce.interfaces.api.adminapi.apis.auth

import com.hiel.commerce.common.domains.seller.Seller
import com.hiel.commerce.common.domains.user.User
import com.hiel.commerce.common.exceptions.ResultCode
import com.hiel.commerce.common.exceptions.ServiceException
import com.hiel.commerce.common.utilities.Regex
import com.hiel.commerce.common.utilities.isNotNullValidLengthTrimmed
import com.hiel.commerce.common.utilities.regexMatches

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
) {
    fun validate() {
        if (!email.regexMatches(Regex.EMAIL)) {
            throw ServiceException(ResultCode.Auth.INVALID_FORMAT_EMAIL)
        }
        if (!password.isNotNullValidLengthTrimmed(min = User.PASSWORD_MINIMUM_LENGTH)) {
            throw ServiceException(
                resultCode = ResultCode.Auth.LENGTH_TOO_SHORT_PASSWORD,
                args = arrayOf(User.PASSWORD_MINIMUM_LENGTH),
            )
        }
        if (!name.isNotNullValidLengthTrimmed(min = Seller.NAME_MINIMUM_LENGTH)) {
            throw ServiceException(
                resultCode = ResultCode.Auth.LENGTH_TOO_SHORT_NAME,
                args = arrayOf(Seller.NAME_MINIMUM_LENGTH),
            )
        }
    }
}

data class CertificateSignupRequest(
    val signupToken: String,
)
