package com.hiel.commerce.common.utilities.mail

interface MailTemplateParams

sealed class MailTemplate<T : MailTemplateParams>(
    open val params: T? = null,
) {
    protected abstract fun subject(): String
    protected abstract fun text(): String
    fun getSubject() = subject().trimIndent()
    fun getText() = text().trimIndent()

    class SignupCertificate(
        override val params: Params,
    ) : MailTemplate<SignupCertificate.Params>(params) {
        data class Params(
            val certificationUrlHost: String,
            val token: String,
        ) : MailTemplateParams

        override fun subject(): String =
            "이메일 인증"

        override fun text(): String = with(params) {
            """
            ${certificationUrlHost}/admin/auths/signup/certificate?token=${token}
            """
        }
    }
}
