/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
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
