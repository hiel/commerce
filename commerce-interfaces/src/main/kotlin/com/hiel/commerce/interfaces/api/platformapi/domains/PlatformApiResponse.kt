package com.hiel.commerce.interfaces.api.platformapi.domains

import com.hiel.commerce.common.exceptions.ResultCode

object PlatformApiResponseFactory {
    fun <D> success(
        data: D? = null,
    ): PlatformApiResponse<D> = PlatformApiResponse(resultCode = ResultCode.Common.SUCCESS, data = data)

    fun <D> failure(
        resultCode: ResultCode,
        message: String? = null,
        data: D? = null,
    ): PlatformApiResponse<D> = PlatformApiResponse(resultCode = resultCode, message = message, data = data)
}

abstract class BasePlatformApiResponse<T>(
    open val resultCode: ResultCode,
    open val message: String? = null,
    open val data: T? = null
)

data class PlatformApiResponse<D>(
    override val resultCode: ResultCode,
    override val message: String? = null,
    override val data: D? = null
) : BasePlatformApiResponse<D>(resultCode, message, data)
