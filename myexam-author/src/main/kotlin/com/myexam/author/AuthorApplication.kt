package com.myexam.author

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthorApplication

fun main(args: Array<String>) {
	runApplication<AuthorApplication>(*args)
}
