package com.hiel.commerce.`interface`.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.hiel.commerce.api", "com.hiel.commerce.common"])
@SpringBootApplication
class CommerceApiApplication

fun main(args: Array<String>) {
    runApplication<CommerceApiApplication>(*args)
}
