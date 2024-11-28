package com.hiel.commerce.interfaces.configurations

import com.hiel.commerce.interfaces.domains.auth.UserDetailsImpl
import java.util.Optional
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuditorAwareImpl : AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return Optional.ofNullable(if (principal is UserDetailsImpl) principal.id else null)
    }
}
