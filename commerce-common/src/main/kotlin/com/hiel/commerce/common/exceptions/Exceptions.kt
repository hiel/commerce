package com.hiel.commerce.common.exceptions

class ServiceException(
    override val resultCode: ResultCode = ResultCode.Common.FAIL,
    override val data: Any? = null,
    vararg args: Any,
) : BaseException(resultCode = resultCode, data = data, args = args)
