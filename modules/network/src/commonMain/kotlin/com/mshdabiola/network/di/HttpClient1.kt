package com.mshdabiola.network.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect val httpClient: HttpClient
expect fun <T : HttpClientEngineConfig> HttpClientConfig<T>.initPlatform()

fun <T : HttpClientEngineConfig> HttpClientConfig<T>.init() {
    install(Resources)
    initPlatform()
    install(ContentNegotiation) {
        json(
            Json {
                this.ignoreUnknownKeys = true
            },
        )
    }
//    defaultRequest {
//        headers {
//            this[HttpHeaders.Authorization] = "Bearer ${Config.token}"
//            this[HttpHeaders.Accept] = "application/json"
//            this[HttpHeaders.ContentType] = "application/json"
//        }
//        url {
//            host = "api.spotify.com"
//            protocol = URLProtocol.HTTPS
//        }
//    }
    install(UserAgent) {
        agent = "my app"
    }
    install(HttpRequestRetry) {
        retryOnServerErrors(5)
        exponentialDelay()
    }
}
