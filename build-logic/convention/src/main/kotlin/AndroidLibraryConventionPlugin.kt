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
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureFlavors
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configureKotlinMultiplatform
import com.mshdabiola.app.configurePrintApksTask
import com.mshdabiola.app.disableUnnecessaryAndroidTests
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.android.library")
                apply("mshdabiola.android.lint")
                apply("org.jetbrains.kotlin.plugin.power-assert")
                apply("mshdabiola.spotless")
            }

            extensions.configure<PowerAssertGradleExtension> {
                functions.set(
                    listOf(
                        "kotlin.assert",
                        "kotlin.test.assertTrue",
                        "kotlin.test.assertEquals",
                        "kotlin.test.assertNull",
                    ),
                )
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35

                configureFlavors(this)

                configureGradleManagedDevices(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                configureKotlinMultiplatform(this)

                with(sourceSets) {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("koin.core").get())
                        implementation(libs.findLibrary("kermit").get())
                        implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                    }

                    androidMain.dependencies {
                        implementation(libs.findLibrary("koin.android").get())
                    }
                    jvmMain.dependencies {
                        implementation(libs.findLibrary("slf4j.simple").get())
                    }

                    jvmTest.dependencies {
                        implementation(kotlin("test"))
                        implementation(libs.findLibrary("kotlinx.coroutines.test").get())

                    }
                }
            }
        }
    }
}
