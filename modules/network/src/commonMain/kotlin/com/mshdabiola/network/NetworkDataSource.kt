package com.mshdabiola.network

import com.mshdabiola.network.model.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.parametersOf
import kotlinx.serialization.json.Json

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
            wikiClient.get("w/api.php?action=query&format=json&formatversion=2&generator=search&prop=description|pageimages&piprop=thumbnail&pithumbsize=70&gsrnamespace=14&gsrsearch=abiola&gsrlimit=4&gsroffset=4")
        val string: Response = if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            throw Exception("Error occur")
        }

        return string
    }

    override suspend fun searchCategory(search: String, limit: Int, offset: Int): Response {
        val parameter = arrayOf(
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
        // enerator=allcategories&prop=categoryinfo|description|pageimages&piprop=thumbnail&pithumbsize=70
        val parameter = arrayOf(
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

    // https://commons.wikimedia.org/w/api.php?action=query&format=json&prop=imageinfo&exportschema=0.11&continue=grncontinue&generator=random&formatversion=2&iiprop=timestamp%7Cmediatype%7Cmime%7Curl%7Cuser%7Cuserid&iilimit=6&grnlimit=12&grncontinue=0.573993798555%7C0.573994320846%7C29009580%7C0
    override suspend fun getTimeline(
        limit: Int,
        continuation: String,
    ): Response {
        // iiprop=timestamp%7Cmediatype%7Cmime%7Curl%7Cuser%7Cuserid&iilimit=6&grnlimit=12&grncontinue=0.573993798555%7C0.573994320846%7C29009580%7C0
        val parameter = arrayOf(
            "generator" to listOf("random"),
            "prop" to listOf("imageinfo"),
            "iiprop" to listOf("mediatype|mime|user|userid|url|timestamp|sha1"),
            "iilimit" to listOf("6"),
            "grnlimit" to listOf(limit.toString()),
            "grncontinue" to listOf(continuation.ifBlank { "0.573993798555|0.57399474331|62056655|0" }),
            "continue" to listOf("grncontinue||"),
        )

        val res = Json.decodeFromString<Response>(res)
        return getCommonResponse(parameter)
    }

    private suspend fun getCommonResponse(parameterArrays: Array<Pair<String, List<String>>>): Response {
        val parameter = parametersOf(
            "action" to listOf("query"),
            "format" to listOf("json"),
            "formatversion" to listOf("2"),
            *parameterArrays,
        )
        val url = URLBuilder(
            protocol = URLProtocol.HTTPS,
            host = commonHost,
            parameters = parameter,
            pathSegments = listOf("w", "api.php"),
        ).build()
        val response =
            try {
                val incomingResponse = wikiClient
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

    val res =
        "{\"batchcomplete\":true,\"continue\":{\"grncontinue\":\"0.573993798541|0.573994755633|50538576|0\",\"continue\":\"grncontinue||\"},\"query\":{\"pages\":[{\"pageid\":9280251,\"ns\":6,\"title\":\"File:The Arkle, Morley. - geograph.org.uk - 118760.jpg\",\"imagerepository\":\"local\",\"imageinfo\":[{\"timestamp\":\"2010-01-31T12:40:48Z\",\"user\":\"GeographBot\",\"userid\":910182,\"url\":\"https://upload.wikimedia.org/wikipedia/commons/8/81/The_Arkle%2C_Morley._-_geograph.org.uk_-_118760.jpg\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:The_Arkle,_Morley._-_geograph.org.uk_-_118760.jpg\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=9280251\",\"sha1\":\"cbb2533afe66433fbc73712afeae75205cee0268\",\"mime\":\"image/jpeg\",\"mediatype\":\"BITMAP\"}]},{\"pageid\":22033961,\"ns\":6,\"title\":\"File:Pontaillac baign4.JPG\",\"imagerepository\":\"local\",\"imageinfo\":[{\"timestamp\":\"2012-10-12T20:39:06Z\",\"user\":\"Jack ma\",\"userid\":633518,\"url\":\"https://upload.wikimedia.org/wikipedia/commons/3/3c/Pontaillac_baign4.JPG\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:Pontaillac_baign4.JPG\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=22033961\",\"sha1\":\"67e0e4ee581bfe3be5bbb0f2ee751ce514d994d7\",\"mime\":\"image/jpeg\",\"mediatype\":\"BITMAP\"}]},{\"pageid\":62056655,\"ns\":6,\"title\":\"File:Bury Tomorrow - Elbriot 2017 18.jpg\",\"imagerepository\":\"local\",\"imageinfo\":[{\"timestamp\":\"2017-08-31T17:16:39Z\",\"user\":\"Huhu Uet\",\"userid\":320836,\"url\":\"https://upload.wikimedia.org/wikipedia/commons/7/74/Bury_Tomorrow_-_Elbriot_2017_18.jpg\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:Bury_Tomorrow_-_Elbriot_2017_18.jpg\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=62056655\",\"sha1\":\"c68186f6323525a85bc9b51f19895fe45f4a6561\",\"mime\":\"image/jpeg\",\"mediatype\":\"BITMAP\"}]},{\"pageid\":84209989,\"ns\":6,\"title\":\"File:CrasnaDealGJ (98).JPG\",\"imagerepository\":\"local\",\"imageinfo\":[{\"timestamp\":\"2015-07-08T17:12:09Z\",\"user\":\"Țetcu Mircea Rareș\",\"userid\":763498,\"url\":\"https://upload.wikimedia.org/wikipedia/commons/0/02/CrasnaDealGJ_%2898%29.JPG\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:CrasnaDealGJ_(98).JPG\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=84209989\",\"sha1\":\"7451847748c20b1710899572b5c90b86bb99ee56\",\"mime\":\"image/jpeg\",\"mediatype\":\"BITMAP\"},{\"timestamp\":\"2011-10-07T17:38:36Z\",\"user\":\"Țetcu Mircea Rareș\",\"userid\":763498,\"url\":\"https://upload.wikimedia.org/wikipedia/commons/archive/0/02/20111007173836%21CrasnaDealGJ_%2898%29.JPG\",\"descriptionurl\":\"https://commons.wikimedia.org/wiki/File:CrasnaDealGJ_(98).JPG\",\"descriptionshorturl\":\"https://commons.wikimedia.org/w/index.php?curid=84209989\",\"sha1\":\"c87cf69dc011faa99f459b189b2f62961eb63fcf\",\"mime\":\"image/jpeg\",\"mediatype\":\"BITMAP\"}]}]}}"
}
