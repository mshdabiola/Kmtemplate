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
package com.mshdabiola.data


sealed class Platform(val identifier: String) {
    object Ios : Platform(identifier = "Ios")
    object Web : Platform(identifier ="Web")
    data class Desktop(val os: String,val javaVersion: String) : Platform("Desktop")
    sealed class Android(open val name: String, open val sdk: Int) : Platform(identifier ="Android") {
        data class FossReliant(
            override val name: String,
            override val sdk: Int,
        ) : Android(name, sdk)

        data class GooglePlay(
            override val name: String,
            override val sdk: Int,
            ) : Android(name, sdk)

    }
}

expect fun getPlatform(): Platform
