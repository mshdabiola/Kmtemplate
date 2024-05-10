package com.mshdabiola.network

import com.mshdabiola.network.model.Response

interface INetworkDataSource {
    suspend fun getRecommendation(): List<String>

    suspend fun goToGoogle(): Response

    suspend fun searchCategory(search: String, limit: Int, offset: Int): Response

    suspend fun searchCategoriesForPrefix(
        prefix: String,
        limit: Int,
        offset: Int,
    ): Response

    //
//
//    @GET("w/api.php?action=query&format=json&formatversion=2&generator=allcategories&prop=categoryinfo|description|pageimages&piprop=thumbnail&pithumbsize=70")
//    fun searchCategoriesForPrefix(
//        @Query("gacprefix") prefix: String?,
//        @Query("gaclimit") itemLimit: Int,
//        @Query("gacoffset") offset: Int
//    ): Single<MwQueryResponse>
//
    suspend fun getCategoriesByName(
        prefix: String,
        suffix: String,
        limit: Int,
        offset: Int,
    ): Response

    //
//    @GET("w/api.php?action=query&format=json&formatversion=2&generator=allcategories&prop=categoryinfo|description|pageimages&piprop=thumbnail&pithumbsize=70")
//    fun getCategoriesByName(
//        @Query("gacfrom") startingCategory: String?,
//        @Query("gacto") endingCategory: String?,
//        @Query("gaclimit") itemLimit: Int,
//        @Query("gacoffset") offset: Int
//    ): Single<MwQueryResponse>
//
    suspend fun getSubCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response

    //    @GET("w/api.php?action=query&format=json&formatversion=2&generator=categorymembers&gcmtype=subcat&prop=info&gcmlimit=50")
//    fun getSubCategoryList(
//        @Query("gcmtitle") categoryName: String,
//        @QueryMap(encoded = true) continuation: Map<String, String>
//    ): Single<MwQueryResponse>
//
    suspend fun getParentCategoryList(
        categoryName: String,
        continuation: Map<String, String>,
    ): Response
//    @GET("w/api.php?action=query&format=json&formatversion=2&generator=categories&prop=info&gcllimit=50")
//    fun getParentCategoryList(
//        @Query("titles") categoryName: String?,
//        @QueryMap(encoded = true) continuation: Map<String, String>
//    ): Single<MwQueryResponse>

    suspend fun getTimeline(limit: Int, continuation: String): Response
}
