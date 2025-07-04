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
