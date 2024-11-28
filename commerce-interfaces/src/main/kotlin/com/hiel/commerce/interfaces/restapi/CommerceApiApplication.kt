package com.hiel.commerce.interfaces.restapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.hiel.commerce.interfaces", "com.hiel.commerce.common", "com.hiel.commerce.service"])
@SpringBootApplication
class CommerceApiApplication

fun main(args: Array<String>) {
    runApplication<CommerceApiApplication>(*args)
}
