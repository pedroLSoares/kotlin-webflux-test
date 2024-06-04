package com.example.kotlin_perf_test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinPerfTestApplication

fun main(args: Array<String>) {
	runApplication<KotlinPerfTestApplication>(*args)
}
