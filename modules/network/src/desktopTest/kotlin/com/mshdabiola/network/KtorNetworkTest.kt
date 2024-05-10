/*
 *abiola 2024
 */

package com.mshdabiola.network

import com.mshdabiola.network.di.init
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KtorNetworkTest {

    private lateinit var ktorNetwork: NetworkDataSource

    @Before
    fun setUp() {
        val engine = MockEngine { re ->
            respond(
                content = ByteReadChannel("""{"id":98,"name":"abiola"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val client = Client.get(engine)
        ktorNetwork = NetworkDataSource(HttpClient(CIO) { init() })
    }

    @After
    fun close() {
    }

    @Test
    fun get() = runTest {
        val model = ktorNetwork.goToGoogle()
        println(model)
        // assertEquals(model.id, 98)
    }

    @Test
    fun searchCategoryTest() = runTest {
        val category = ktorNetwork.searchCategory("mosque", 10, 100)
        println(category)
    }

    @Test
    fun searchCategoryForPrefixTest() = runTest {
        val category = ktorNetwork.searchCategoriesForPrefix("mos", 10, 100)
        println(category)
    }

    @Test
    fun timelineTest() = runTest {
        val category = ktorNetwork.getTimeline(4, "")
        println(category)
    }
}
