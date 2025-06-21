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
package com.mshdabiola.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    //    contentType,
    store,
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class Flavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionNameSuffix: String? = null,
) {
    //    demo(FlavorDimension.contentType, applicationIdSuffix = ".demo", "-demo"),
//    prod(FlavorDimension.contentType),
    fossReliant(FlavorDimension.store, applicationIdSuffix = ".foss"),
    googlePlay(FlavorDimension.store, applicationIdSuffix = ".play", versionNameSuffix = "-play"),
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: Flavor) -> Unit = {},
) {
    commonExtension.apply {
//        flavorDimensions += FlavorDimension.contentType.name
        flavorDimensions += FlavorDimension.store.name

        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                        }
                        if (it.versionNameSuffix != null) {
                            this.versionNameSuffix = it.versionNameSuffix
                        }
                        if (it == Flavor.googlePlay) {
                            with(pluginManager) {
                                apply("mshdabiola.android.application.firebase")
                            }
                        }
                    }
                }
            }
        }
    }
}
