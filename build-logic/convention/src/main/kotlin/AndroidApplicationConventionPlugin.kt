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
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import com.mshdabiola.app.configureBadgingTasks
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configureKotlinMultiplatform
import com.mshdabiola.app.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import kotlin.text.set

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.android.application")
                apply("mshdabiola.kover")
                apply("mshdabiola.android.lint")
                apply("com.dropbox.dependency-guard")
                apply("mshdabiola.spotless")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
                testOptions.animationsDisabled = true
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
                configureBadgingTasks(extensions.getByType<BaseExtension>(), this)
            }
            extensions.configure<KotlinMultiplatformExtension> {
                jvmToolchain(21)

                androidTarget()
                // jvm("desktop")
                jvm()
                @OptIn(ExperimentalWasmDsl::class)
                wasmJs {
                    outputModuleName.set("composeApp")
                    browser {
                        val rootDirPath = project.rootDir.path
                        val projectDirPath = project.projectDir.path
                        commonWebpackConfig {
                            outputFileName = "composeApp.js"
                            devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                                static = (static ?: mutableListOf()).apply {
                                    // Serve sources to debug inside browser
                                    add(rootDirPath)
                                    add(projectDirPath)
                                }
                            }
                        }
                    }
                    binaries.executable()
                }

            }
        }
    }
}
