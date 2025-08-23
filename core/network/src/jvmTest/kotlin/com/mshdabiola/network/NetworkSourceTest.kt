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
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.fail

class NetworkDataSourceTest {

    private val testJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun `goToGoogle successfully returns content from mock engine`() = runTest {
        val expectedResponseContent = "<html><body>Mock Google Page</body></html>"
        val mockEngine = MockEngine { request ->
            assertEquals("http://google.com", request.url.toString())
            respond(
                content = expectedResponseContent,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "text/html"),
            )
        }

        val httpClient = HttpClient(mockEngine)
        val networkDataSource = RealNetworkDataSource(httpClient)
        val result = networkDataSource.goToGoogle()

        assertEquals(expectedResponseContent, result)
        mockEngine.close()
    }

    @Test
    fun `goToGoogle handles HTTP error from mock engine`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals("http://google.com", request.url.toString())
            respond(
                content = "Error: Not Found",
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "text/plain"),
            )
        }

        val httpClient = HttpClient(mockEngine)
        val networkDataSource = RealNetworkDataSource(httpClient)

        try {
            networkDataSource.goToGoogle()
            fail("Expected an exception to be thrown for HTTP error")
        } catch (e: ClientRequestException) {
            assertEquals(HttpStatusCode.NotFound, e.response.status)
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    @Test
    fun `goToGoogle handles network error (engine failure)`() = runTest {
        val mockEngine = MockEngine {
            // No specific URL check needed here as we are testing general IOException
            throw java.io.IOException("Simulated network problem")
        }

        val httpClient = HttpClient(mockEngine)
        val networkDataSource = RealNetworkDataSource(httpClient)

        try {
            networkDataSource.goToGoogle()
            fail("Expected an exception due to network error")
        } catch (e: java.io.IOException) {
            assertEquals("Simulated network problem", e.message)
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    @Test
    fun `getLatestKmtemplateRelease successfully returns release info including assets`() = runTest {
        val expectedReleaseJson = getResourceAsText("github.json") // Make sure the path is correct
        val gitHubReleaseInfo = testJson.decodeFromString(
            ListSerializer(GitHubReleaseInfo.serializer()),
            expectedReleaseJson,
        ).first()

        val mockEngine = MockEngine { request ->
            assertEquals(
                "https://api.github.com/repos/mshdabiola/kmtemplate/releases",
                request.url.toString(),
            )
            respond(
                content = expectedReleaseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(testJson)
            }
        }

        val networkDataSource = RealNetworkDataSource(httpClient)
        val result = networkDataSource.getLatestKmtemplateRelease()

        assertNotNull(result)
        assertEquals(gitHubReleaseInfo.tagName, result.tagName)
        assertEquals(gitHubReleaseInfo.releaseName, result.releaseName)
        assertEquals(gitHubReleaseInfo.body, result.body)
        assertTrue(result.prerelease == true)
        assertEquals(
            gitHubReleaseInfo.htmlUrl,
            result.htmlUrl,
        )

        assertNotNull(result.assets)
        assertEquals(gitHubReleaseInfo.assets?.size, result.assets.size)

        val firstAsset = result.assets[0]
        val expectAssetFirst = gitHubReleaseInfo.assets?.first()
        assertNotNull(firstAsset)
        assertEquals(
            expectAssetFirst?.browserDownloadUrl,
            firstAsset.browserDownloadUrl,
        )
        assertEquals(expectAssetFirst?.size, firstAsset.size)

        val secondAsset = result.assets[1]
        val expectAssetSecond = gitHubReleaseInfo.assets?.getOrNull(1)

        assertNotNull(secondAsset)
        assertEquals(
            expectAssetSecond?.browserDownloadUrl,
            secondAsset.browserDownloadUrl,
        )
        assertEquals(expectAssetSecond?.size, secondAsset.size)

        mockEngine.close()
    }

    @Test
    fun `getLatestKmtemplateRelease handles HTTP error`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals(
                "https://api.github.com/repos/mshdabiola/kmtemplate/releases",
                request.url.toString(),
            )
            respond(
                content = "Error: Repository Not Found",
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "text/plain"),
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(testJson)
            }
        }
        val networkDataSource = RealNetworkDataSource(httpClient)

        try {
            networkDataSource.getLatestKmtemplateRelease()
            fail("Expected ClientRequestException for HTTP error")
        } catch (e: ClientRequestException) {
            assertEquals(HttpStatusCode.NotFound, e.response.status)
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    @Test
    fun `getLatestKmtemplateRelease handles network error`() = runTest {
        val mockEngine = MockEngine {
            // No specific URL check needed here for the general IOException test for this endpoint
            throw java.io.IOException("Simulated network problem for GitHub API")
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(testJson)
            }
        }
        val networkDataSource = RealNetworkDataSource(httpClient)

        try {
            networkDataSource.getLatestKmtemplateRelease()
            fail("Expected IOException for network error")
        } catch (e: java.io.IOException) {
            assertEquals("Simulated network problem for GitHub API", e.message)
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    @Test
    fun `getLatestKmtemplateRelease throws NoSuchElementException for empty release list`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals(
                "https://api.github.com/repos/mshdabiola/kmtemplate/releases",
                request.url.toString(),
            )
            respond(
                content = "[]", // Empty list of releases
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(testJson)
            }
        }
        val networkDataSource = RealNetworkDataSource(httpClient)

        try {
            networkDataSource.getLatestKmtemplateRelease()
            fail("Expected NoSuchElementException for empty release list")
        } catch (e: NoSuchElementException) {
            assertEquals("No releases found for mshdabiola/kmtemplate", e.message)
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    // Helper function to read a resource file
    fun getResourceAsText(path: String): String {
        val stream = ClassLoader.getSystemResourceAsStream(path)
        requireNotNull(stream) { "Resource not found: $path" }
        return InputStreamReader(stream).readText()
    }
}
