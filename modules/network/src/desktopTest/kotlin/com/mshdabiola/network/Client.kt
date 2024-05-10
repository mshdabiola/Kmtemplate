/*
 *abiola 2024
 */

package com.mshdabiola.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json

object Client {
    fun get(httpClientEngine: HttpClientEngine) = HttpClient(httpClientEngine) {
        install(Resources)

        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            this.url("")
        }
        install(UserAgent) {
            agent = "my app"
        }
    }
}
