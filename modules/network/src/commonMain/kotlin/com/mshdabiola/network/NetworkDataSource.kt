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

import com.mshdabiola.network.model.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parametersOf
import io.ktor.http.plus
import kotlinx.io.IOException

internal class NetworkDataSource constructor(
    private val wikiClient: HttpClient,
) : INetworkDataSource {
    private val commonHost = "commons.wikimedia.org"

    override suspend fun getRecommendation(): List<String> {
//        val response = httpClient.get(
//            Request.Recommendations(
//                limit = "10",
//                market = "NG",
//                seed_artists = "4NHQUGzhtTLFvgF5SZesLK",
//                seed_genres = "classical",
//                seed_tracks = "0c6xIDDpzE81m2q797ordA"
//            )
//        )
//        val netWorkTracks: PagingNetWorkTracks = if (response.status == HttpStatusCode.OK) {
//            response.body()
//        } else {
//            val message: Message = response.body()
//            throw Exception(message.error.message)
//        }
//
//
//        return netWorkTracks.tracks
//    }
        TODO()
    }

    override suspend fun goToGoogle(): Response {
        // piprop=thumbnail&pithumbsize=70&gsrnamespace=14&gsrsearch=abiola&gsrlimit=4&gsroffset=4
        val response =
            wikiClient.get(
                "w/api.php?action=query&format=json&formatversion=2&generator" +
                    "=search&prop=description|pageimages&piprop=thumbnail&pithumbsize=" +
                    "70&gsrnamespace=14&gsrsearch=abiola&gsrlimit=4&gsroffset=4",
            )
        val string: Response =
            if (response.status == HttpStatusCode.OK) {
                response.body()
            } else {
                throw IOException("Error occur")
            }

        return string
    }

    override suspend fun searchCategory(
        search: String,
        limit: Int,
        offset: Int,
    ): Response {
        val parameter =
            parametersOf(
                "generator" to listOf("search"),
                "prop" to listOf("description|pageimages"),
                "piprop" to listOf("thumbnail"),
                "pithumbsize" to listOf("70"),
                "gsrnamespace" to listOf("14"),
                "gsrsearch" to listOf(search),
                "gsrlimit" to listOf(limit.toString()),
                "gsroffset" to listOf(offset.toString()),
            )

        return getCommonResponse(parameter)
    }

    override suspend fun searchCategoriesForPrefix(
        prefix: String,
        limit: Int,
        offset: Int,
    ): Response {
        val parameter =
            parametersOf(
                "generator" to listOf("allcategories"),
                "prop" to listOf("categoryinfo|description|pageimages"),
                "piprop" to listOf("thumbnail"),
                "pithumbsize" to listOf("70"),
                "gacprefix" to listOf(prefix),
                "gaclimit" to listOf(limit.toString()),
                "gacoffset" to listOf(offset.toString()),
            )

        return getCommonResponse(parameter)
    }

    override suspend fun getCategoriesByName(
        prefix: String,
        suffix: String,
        limit: Int,
        offset: Int,
    ): Response {
        TODO("Not yet implemented")
    }

    override suspend fun getSubCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response {
        TODO("Not yet implemented")
    }

    override suspend fun getParentCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response {
        val urlBuilder = URLBuilder()
        urlBuilder.build()
        wikiClient.get(urlBuilder.build())
        TODO("Not yet implemented")
    }

    override suspend fun getTimeline(
        limit: Int,
        continuation: String,
    ): Response {
        val parameter =
            parametersOf(
                "generator" to listOf("random"),
                "prop" to listOf("imageinfo"),
                "iiprop" to listOf("mediatype|mime|user|userid|url|timestamp|sha1"),
                "iilimit" to listOf("6"),
                "grnlimit" to listOf(limit.toString()),
                "grncontinue" to
                    listOf(
                        continuation
                            .ifBlank { "0.573993798555|0.57399474331|62056655|0" },
                    ),
                "continue" to listOf("grncontinue||"),
            )

        return getCommonResponse(parameter)
    }

    private suspend fun getCommonResponse(parameterArrays: Parameters): Response {
        val parameter =
            parametersOf(
                "action" to listOf("query"),
                "format" to listOf("json"),
                "formatversion" to listOf("2"),
            )
        val url =
            URLBuilder(
                protocol = URLProtocol.HTTPS,
                host = commonHost,
                parameters = parameter.plus(parameterArrays),
                pathSegments = listOf("w", "api.php"),
            ).build()
        val response =
            try {
                val incomingResponse =
                    wikiClient
                        .get(url)
                if (incomingResponse.status != HttpStatusCode.OK) {
                    throw IOException("Http Error")
                }
                incomingResponse.body<Response>()
            } catch (e: IOException) {
                // Catch other general I/O errors (like UnknownHostException if not caught more specifically)
                // Rethrow as is, or wrap in a custom domain exception
                throw IOException("Network I/O error: ${e.message}", e)
            }
        return response
    }
}
