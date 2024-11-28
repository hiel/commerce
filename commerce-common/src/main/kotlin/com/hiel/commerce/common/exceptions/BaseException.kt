package com.hiel.commerce.common.exceptions

open class BaseException(
    open val resultCode: ResultCode,
    open val data: Any? = null,
    vararg args: Any,
) : RuntimeException(resultCode.getMessage(*args))
