package com.hiel.commerce.interfaces.api.adminapi.domains

import com.hiel.commerce.common.exceptions.ResultCode

object AdminApiResponseFactory {
    fun <D> success(
        data: D? = null,
    ): AdminApiResponse<D> = AdminApiResponse(resultCode = ResultCode.Common.SUCCESS, data = data)

    fun <D> failure(
        resultCode: ResultCode,
        message: String? = null,
        data: D? = null,
    ): AdminApiResponse<D> = AdminApiResponse(resultCode = resultCode, message = message, data = data)
}

abstract class BaseAdminApiResponse<T>(
    open val resultCode: ResultCode,
    open val message: String? = null,
    open val data: T? = null
)

data class AdminApiResponse<D>(
    override val resultCode: ResultCode,
    override val message: String? = null,
    override val data: D? = null
) : BaseAdminApiResponse<D>(resultCode, message, data)
