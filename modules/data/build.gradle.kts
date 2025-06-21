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
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
}

android {
    namespace = "com.mshdabiola.data"
}



kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))
                implementation(project(":modules:analytics"))
                implementation(libs.koin.core)
                implementation(project(":modules:model"))
                api(project(":modules:database"))
                api(project(":modules:datastore"))
                api(project(":modules:network"))
                implementation(libs.kotlinx.coroutines.core)
              //  implementation(libs.paging.common)
            }
        }

    }
}