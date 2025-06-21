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
package com.mshdabiola.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    @SerialName("description")
    val description: String? = null,
    @SerialName("descriptionsource")
    val descriptionsource: String? = null,
    @SerialName("index")
    val index: Int? = null,
    @SerialName("ns")
    val ns: Int? = null,
    @SerialName("pageid")
    val pageid: Int? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("imageinfo")
    val imageinfo: List<Imageinfo?>? = null,
    @SerialName("imagerepository")
    val imagerepository: String? = null,
)
