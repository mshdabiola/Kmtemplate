package com.mshdabiola.network

import com.mshdabiola.network.request.WikiAPI
import io.ktor.resources.href
import io.ktor.resources.serialization.ResourcesFormat
import org.junit.Test

class WIkiApiTest {
    @Test
    fun searchCategoryTest() {
        val text = href(
            ResourcesFormat(),
            WikiAPI.SearchCategory(gsrlimit = 4, gsroffset = 4, gsrsearch = "abiola"),
        )
        println(text)
    }
}
