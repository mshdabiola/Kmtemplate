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

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class NetworkDataSourceTest {

    @Test
    fun `goToGoogle successfully returns content from mock engine`() = runTest {
        val expectedResponseContent = "<html><body>Mock Google Page</body></html>"
        val mockEngine = MockEngine { request ->
            // Check if the request URL is what we expect
            assertEquals("http://google.com", request.url.toString())
            respond(
                content = expectedResponseContent,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "text/html"),
            )
        }

        val httpClient = HttpClient(mockEngine) {
            // If NetworkDataSource installs plugins like ContentNegotiation,
            // you might need to install them here too for the test client
            // if they affect how the raw response is processed into a String.
            // For a simple String body as in NetworkDataSource, it's often not needed.
        }

        val networkDataSource = NetworkDataSource(httpClient)
        val result = networkDataSource.goToGoogle()

        assertEquals(expectedResponseContent, result)
        mockEngine.close() // Good practice to close the engine
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
        val networkDataSource = NetworkDataSource(httpClient)

        try {
            networkDataSource.goToGoogle()
            fail("Expected an exception to be thrown for HTTP error")
        } catch (e: Exception) {
            // Ktor's default behavior for non-2xx responses when trying to .body<String>()
            // might be to throw an exception if expectSuccess is true (default) or during deserialization.
            // The exact exception can vary based on Ktor client configuration (e.g. `expectSuccess = true`).
            // For a simple string body, often the exception chain might start with ClientRequestException
            // if expectSuccess is true, or BodyDeserializeException if it tries to read the body of an error.
            assertTrue(true, "Correctly threw an exception for HTTP error: ${e.message}")
        } catch (e: io.ktor.client.plugins.ClientRequestException) {
            // This is another common exception if expectSuccess = true (default in many setups)
            assertEquals(HttpStatusCode.NotFound, e.response.status)
            assertTrue(true, "Correctly threw ClientRequestException for HTTP error: ${e.message}")
        } catch (e: Exception) {
            fail("An unexpected exception was thrown: ${e::class.simpleName} - ${e.message}")
        } finally {
            mockEngine.close()
        }
    }

    @Test
    fun `goToGoogle handles network error (engine failure)`() = runTest {
        val mockEngine = MockEngine {
            // Simulate a network failure by throwing an IOException
            throw java.io.IOException("Simulated network problem")
        }

        val httpClient = HttpClient(mockEngine)
        val networkDataSource = NetworkDataSource(httpClient)

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
}
