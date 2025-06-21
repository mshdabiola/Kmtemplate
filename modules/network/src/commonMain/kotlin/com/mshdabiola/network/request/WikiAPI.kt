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
