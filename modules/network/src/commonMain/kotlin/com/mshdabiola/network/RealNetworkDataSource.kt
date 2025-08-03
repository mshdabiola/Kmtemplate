package com.mshdabiola.network

import com.mshdabiola.network.model.GitHubReleaseInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

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
            "https://api.github.com/repos/mshdabiola/kmtemplate/releases/latest")
        if (!response.status.isSuccess()) {
            // You might want to throw a more specific exception or return a sealed result type
            // to handle different error cases (e.g., 404 Not Found if no releases exist)
            throw ClientRequestException(response,
                "Failed to fetch latest release: ${response.status.value}")
        }
        // Assumes your HttpClient is configured with ContentNegotiation and Json { ignoreUnknownKeys = true }
        return response.body()
    }
}
