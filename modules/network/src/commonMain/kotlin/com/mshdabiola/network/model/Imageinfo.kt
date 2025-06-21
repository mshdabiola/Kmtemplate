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
data class Imageinfo(
    @SerialName("descriptionshorturl")
    val descriptionshorturl: String? = null,
    @SerialName("descriptionurl")
    val descriptionurl: String? = null,
    @SerialName("mediatype")
    val mediatype: String? = null,
    @SerialName("mime")
    val mime: String? = null,
    @SerialName("timestamp")
    val timestamp: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("user")
    val user: String? = null,
    @SerialName("userid")
    val userid: Int? = null,
    @SerialName("sha1")
    val id: String? = null,
)
