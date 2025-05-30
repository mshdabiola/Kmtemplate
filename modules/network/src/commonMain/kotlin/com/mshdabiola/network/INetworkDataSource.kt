package com.mshdabiola.network

import com.mshdabiola.network.model.Response

interface INetworkDataSource {
    suspend fun getRecommendation(): List<String>

    suspend fun goToGoogle(): Response

    suspend fun searchCategory(
        search: String,
        limit: Int,
        offset: Int,
    ): Response

    suspend fun searchCategoriesForPrefix(
        prefix: String,
        limit: Int,
        offset: Int,
    ): Response

    suspend fun getCategoriesByName(
        prefix: String,
        suffix: String,
        limit: Int,
        offset: Int,
    ): Response

    suspend fun getSubCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response

    suspend fun getParentCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response

    suspend fun getTimeline(
        limit: Int,
        continuation: String,
    ): Response
}
