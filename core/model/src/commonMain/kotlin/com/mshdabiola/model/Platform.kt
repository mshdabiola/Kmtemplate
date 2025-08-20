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

sealed class Platform {
//    object Ios : Platform()
    object Web : Platform()
    data class Desktop(val os: String, val javaVersion: String) : Platform()
    data class Android(val flavorStr:String, val buildTypeStr: String, val sdk: Int) : Platform(){
        val flavor : Flavor
            get() {
               return when(flavorStr){
                    "googlePlay" -> {
                        Flavor.GooglePlay
                    }
                   else -> {
                        Flavor.FossReliant
                    }

               }
            }
        val buildType : BuildType
        get() {
            return when(buildTypeStr){
                "release" -> {
                    BuildType.Release
                }
                else -> {
                    BuildType.Debug
                }
        }
    }
    }
}

enum class Flavor {
    GooglePlay,
    FossReliant
}

enum class BuildType {
    Release,
    Debug
}
