package com.example.kotlin_perf_test.handler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.flux
import kotlinx.coroutines.reactor.mono
import kotlinx.coroutines.withContext
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import kotlin.jvm.optionals.getOrDefault

@Component
class PingHandler {

    val logger = LogManager.getLogger(PingHandler::class);

    suspend fun pingHandler(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValue("pong").awaitSingle()
    }

    suspend fun processMonoHandler(request: ServerRequest): ServerResponse = coroutineScope {
        val times = request.queryParam("times").getOrDefault("10").toInt()

        val result = fibonacciMono(times)

        ServerResponse.ok().bodyValueAndAwait(result.awaitSingle())
    }

    suspend fun processHandler(request: ServerRequest): ServerResponse = coroutineScope {
        val times = request.queryParam("times").getOrDefault("10").toInt()

        val result = fibonacci(times)

        ServerResponse.ok().bodyValueAndAwait(result)
    }


    private suspend fun fibonacci(n: Int): Int = withContext(Dispatchers.IO) {
        logger.info("current coroutine")
            if (n == 0 || n == 1) {
                n
            } else {
                fibonacci(n - 1) + fibonacci(n - 2)
            }
    }


    private fun fibonacciMono(n: Int): Mono<Int> = mono(Dispatchers.IO) {
            logger.info("current mono")
            if (n == 0 || n == 1) {
                n
            } else {
                fibonacciMono(n - 1).awaitSingle() + fibonacciMono(n - 2).awaitSingle()
            }
        }

}