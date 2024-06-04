package com.example.kotlin_perf_test.config

import com.example.kotlin_perf_test.handler.PingHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfig {

    @Bean
    fun pingController(
        handler: PingHandler
    ) = coRouter {
        GET("/ping", handler::pingHandler)
        GET("/process-mono", handler::processMonoHandler)
        GET("/process", handler::processHandler)
    }
}