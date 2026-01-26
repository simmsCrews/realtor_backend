package com.system.realtor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RealtorApplication

fun main(args: Array<String>) {
    runApplication<RealtorApplication>(*args)
}