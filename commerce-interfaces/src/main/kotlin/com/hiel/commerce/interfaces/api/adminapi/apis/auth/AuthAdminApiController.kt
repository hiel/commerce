package com.hiel.commerce.interfaces.api.adminapi.apis.auth

import com.hiel.commerce.interfaces.api.adminapi.domains.AdminApiResponse
import com.hiel.commerce.interfaces.api.adminapi.domains.AdminApiResponseFactory
import com.hiel.commerce.service.auth.AuthUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/admin/auths")
@RestController
class AuthAdminApiController(
    private val authUseCase: AuthUseCase,
    private val passwordEncoder: PasswordEncoder,

    @Value("\${web-client-url}")
    private val webClientUrl: String,
) {
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: SignupRequest,
    ): AdminApiResponse<Unit> {
        request.validate()
        authUseCase.signup(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            sellerName = request.name,
            certificationUrlHost = webClientUrl,
        )
        return AdminApiResponseFactory.success()
    }

    @PutMapping("/signup/certificate")
    fun certificateSignup(
        @RequestBody request: CertificateSignupRequest,
    ): AdminApiResponse<Unit> {
        authUseCase.certificateSignup(request.signupToken)
        return AdminApiResponseFactory.success()
    }
}
