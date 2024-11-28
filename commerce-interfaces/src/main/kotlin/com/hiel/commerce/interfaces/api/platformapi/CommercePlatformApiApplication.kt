package com.hiel.commerce.interfaces.api.platformapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["com.hiel.commerce.infrastructure.persistence.jpa"])
@EnableJpaRepositories(basePackages = ["com.hiel.commerce.infrastructure.persistence.jpa"])
@SpringBootApplication
class CommercePlatformApiApplication

fun main(args: Array<String>) {
    runApplication<CommercePlatformApiApplication>(*args)
}
