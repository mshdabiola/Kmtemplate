package com.mshdabiola.network

import com.mshdabiola.network.model.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parametersOf

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
                throw Exception("Error occur")
            }

        return string
    }

    override suspend fun searchCategory(
        search: String,
        limit: Int,
        offset: Int,
    ): Response {
        val parameter =
            arrayOf(
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
            arrayOf(
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
            arrayOf(
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

    private suspend fun getCommonResponse(parameterArrays: Array<Pair<String, List<String>>>): Response {
        val parameter =
            parametersOf(
                "action" to listOf("query"),
                "format" to listOf("json"),
                "formatversion" to listOf("2"),
                *parameterArrays,
            )
        val url =
            URLBuilder(
                protocol = URLProtocol.HTTPS,
                host = commonHost,
                parameters = parameter,
                pathSegments = listOf("w", "api.php"),
            ).build()
        val response =
            try {
                val incomingResponse =
                    wikiClient
                        .get(url)
                if (incomingResponse.status != HttpStatusCode.OK) {
                    throw Exception("Http Error")
                }
                incomingResponse.body<Response>()
            } catch (exception: Exception) {
                exception.printStackTrace()
                throw exception
            }

        return response
    }
}
