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
