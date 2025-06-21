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
package com.mshdabiola.model

class JVMPlatform : Platform {
    override val name: String
        get() {
            val operSys = System.getProperty("os.name").lowercase()
            val os =
                if (operSys.contains("win")) {
                    "Windows"
                } else if (operSys.contains("nix") ||
                    operSys.contains("nux") ||
                    operSys.contains("aix")
                ) {
                    "Linux"
                } else if (operSys.contains("mac")) {
                    "MacOS"
                } else {
                    //  Logger.e("PlatformUtil.jvm") { "Unknown platform: $operSys" }
                    "Linux"
                }
            val javaVersion = System.getProperty("java.version")

            return "$os $javaVersion"
        }
}

actual fun getPlatform(): Platform = JVMPlatform()
