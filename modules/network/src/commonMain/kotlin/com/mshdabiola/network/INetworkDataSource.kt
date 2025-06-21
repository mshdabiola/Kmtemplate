/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
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
