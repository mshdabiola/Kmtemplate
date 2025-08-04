/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.network

import com.mshdabiola.network.model.Asset // Import Asset if it's in a different package and needed for direct use in test, though not strictly necessary for this update
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
import kotlinx.serialization.json.Json
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
        val expectedReleaseJson = """{
            "tag_name": "v1.0.0",
            "name": "Initial Release",
            "body": "This is the first release.",
            "html_url": "https://github.com/mshdabiola/kmtemplate/releases/tag/v1.0.0",
            "assets": [
                {
                    "browser_download_url": "https://github.com/mshdabiola/kmtemplate/releases/download/v1.0.0/kmtemplate.apk",
                    "size": 1234567
                },
                {
                    "browser_download_url": "https://github.com/mshdabiola/kmtemplate/releases/download/v1.0.0/source.zip",
                    "size": 7654321
                }
            ]
        }"""
        val mockEngine = MockEngine { request ->
            assertEquals("https://api.github.com/repos/mshdabiola/kmtemplate/releases/latest", request.url.toString())
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
        assertEquals("v1.0.0", result.tagName)
        assertEquals("Initial Release", result.releaseName)
        assertEquals("This is the first release.", result.body)
        assertEquals("https://github.com/mshdabiola/kmtemplate/releases/tag/v1.0.0", result.htmlUrl)

        assertNotNull(result.assets)
        assertEquals(2, result.assets.size)

        val firstAsset = result.assets[0]
        assertNotNull(firstAsset)
        assertEquals("https://github.com/mshdabiola/kmtemplate/releases/download/v1.0.0/kmtemplate.apk", firstAsset?.browserDownloadUrl)
        assertEquals(1234567, firstAsset.size)

        val secondAsset = result.assets.get(1)
        assertNotNull(secondAsset)
        assertEquals("https://github.com/mshdabiola/kmtemplate/releases/download/v1.0.0/source.zip", secondAsset?.browserDownloadUrl)
        assertEquals(7654321, secondAsset.size)

        mockEngine.close()
    }

    @Test
    fun `getLatestKmtemplateRelease handles HTTP error`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals("https://api.github.com/repos/mshdabiola/kmtemplate/releases/latest", request.url.toString())
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
}
