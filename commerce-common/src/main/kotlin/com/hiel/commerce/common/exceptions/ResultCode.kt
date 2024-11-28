package com.hiel.commerce.common.exceptions

interface ResultCode {
    fun getMessage(vararg args: Any): String?

    enum class Common(private val message: String? = null) : ResultCode {
        SUCCESS,
        FAIL("요청에 실패하였습니다."),
        ;

        override fun getMessage(vararg args: Any) = message?.let { String.format(it, *args) }
    }
}
