package com.mshdabiola.network.request

import io.ktor.resources.Resource

@Resource("w/api.php?action=query&format=json&formatversion=2")
class WikiAPI {
    @Resource(
        "/w/api.php?action=query&format=json&formatversion=2&generator=" +
            "search&prop=description|pageimages&piprop=thumbnail&pithumbsize=" +
            "70&gsrnamespace=14",
    )
    data class SearchCategory(
        val gsrsearch: String?,
        val gsrlimit: Int,
        val gsroffset: Int,
    )
}
