package com.hiel.commerce.common.exceptions

interface ResultCode {
    fun getMessage(vararg args: Any): String?

    enum class Common(private val message: String? = null) : ResultCode {
        SUCCESS,
        FAIL("요청에 실패하였습니다."),
        ;

        override fun getMessage(vararg args: Any) = message?.let { String.format(it, *args) }
    }

    enum class Auth(private val message: String?) : ResultCode {
        INVALID_FORMAT_EMAIL("잘못된 형식의 이메일입니다."),
        LENGTH_TOO_SHORT_PASSWORD("비밀번호는 최소 %d자 이상이어야 합니다."),
        LENGTH_TOO_SHORT_NAME("이름은 최소 %d자 이상이어야 합니다."),
        AUTHENTICATION_FAIL("인증에 실패하였습니다."),
        INVALID_TOKEN("잘못된 인증 정보입니다."),
        EXPIRED_TOKEN("만료된 인증 정보입니다."),
        NOT_EXIST_USER("없는 회원입니다"),
        EXPIRED_ACCESS_TOKEN("만료된 인증 정보입니다."),
        DUPLICATED_EMAIL("중복된 이메일이 있습니다."),
        ;

        override fun getMessage(vararg args: Any) = message?.let { String.format(it, *args) }
    }
}
