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
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val koverPlugin = "org.jetbrains.kotlinx.kover"
            pluginManager.apply(koverPlugin)
            val exclude = listOf(
                "app",
                "feature",
                "core",
                "benchmarks",
                "analytics",
                "model",
                "ktlint",
                "designsystem",
                "testing",
            )

            rootProject.subprojects {
                if (exclude.contains(this@subprojects.name)) return@subprojects

//                if (this@subprojects.name == target.name) return@subprojects

                this@subprojects.beforeEvaluate {
                    this@subprojects.pluginManager.apply(koverPlugin)
                }
                target.dependencies.add("kover", this@subprojects)
            }

            configure<KoverProjectExtension> {
                reports {
                    filters {
                        excludes {
//                            androidGeneratedClasses()
                            annotatedBy(
                                // Compose previews
                                "org.jetbrains.compose.ui.tooling.preview.Preview",
                                "Preview",
                                "androidx.compose.ui.tooling.preview.PreviewScreenSizes",
                                "PreviewScreenSizes",
                                "kotlinx.serialization.Serializable",
                                "Serializable",
                            )
                            files(
                                // Navigation helpers
                                "*.*NavigationKt.kt",
                                "*.*MainApp.kt", // Changed from *.*MainAppKt.kt

                            )
                            classes(
                                "*.*State*",

                                // Composable singletons
                                "*.*ComposableSingletons*",
                                // Generated classes related to interfaces with default values
                                "*.*DefaultImpls*",
                                // Databases
                                "*.database.*Database*",
                                // Serializers
                                "*UserDataJsonSerializer*",
                                // Extension functions/classes
                                "*Extension*",
                            )
                            packages(
                                // Dependency injection
                                "*.generated.resources",
                                "*.di",
                                "*.navigation",
                                "*.model",
                            )
                        }
                    }
                }
            }
        }
    }
}
