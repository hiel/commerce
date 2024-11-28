package com.hiel.commerce.interfaces.restapi.domains

import com.hiel.commerce.common.exceptions.ResultCode

object ApiResponseFactory {
    fun <D> success(
        data: D? = null,
    ): ApiResponse<D> = ApiResponse(resultCode = ResultCode.Common.SUCCESS, data = data)

    fun <D> failure(
        resultCode: ResultCode,
        message: String? = null,
        data: D? = null,
    ): ApiResponse<D> = ApiResponse(resultCode = resultCode, message = message, data = data)
}

abstract class BaseApiResponse<T>(
    open val resultCode: ResultCode,
    open val message: String? = null,
    open val data: T? = null
)

data class ApiResponse<D>(
    override val resultCode: ResultCode,
    override val message: String? = null,
    override val data: D? = null
) : BaseApiResponse<D>(resultCode, message, data)
