package com.hiel.commerce.common.domains.user

enum class UserType(val description: String) {
    MASTER("운영자"),
    ADMIN("관리자"),
    SELLER("판매자"),
    CUSTOMER("구매자"),
    ;
}
