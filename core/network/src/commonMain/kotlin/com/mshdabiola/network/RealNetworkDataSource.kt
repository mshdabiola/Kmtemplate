/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.network

import com.mshdabiola.network.model.GitHubReleaseInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
// kotlin.NoSuchElementException is usually available without explicit import

class RealNetworkDataSource(
    private val httpClient: HttpClient,
) : NetworkDataSource {
    override suspend fun goToGoogle(): String {
        val response: HttpResponse = httpClient.get("http://google.com")
        if (!response.status.isSuccess()) {
            throw ClientRequestException(response, "HTTP Error: ${response.status.value}")
        }
        return response.body()
    }

    override suspend fun getLatestKmtemplateRelease(): GitHubReleaseInfo {
        val response: HttpResponse = httpClient.get(
            "https://api.github.com/repos/mshdabiola/kmtemplate/releases",
        )
        if (!response.status.isSuccess()) {
            // You might want to throw a more specific exception or return a sealed result type
            // to handle different error cases (e.g., 404 Not Found if no releases exist)
            throw ClientRequestException(
                response,
                "Failed to fetch releases: ${response.status.value}",
            )
        }
        // Assumes your HttpClient is configured with ContentNegotiation and Json { ignoreUnknownKeys = true }
        val releases: List<GitHubReleaseInfo> = response.body()
        if (releases.isEmpty()) {
            throw NoSuchElementException("No releases found for mshdabiola/kmtemplate")
        }
        return releases.first()
    }
}
