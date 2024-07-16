

import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mshdabiola.android.library")
                apply("mshdabiola.android.library.compose")
                apply("mshdabiola.android.library.jacoco")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner =
                        "com.mshdabiola.testing.TestRunner"
                }
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {


                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                add("testImplementation", project(":modules:testing"))


                add("androidTestImplementation", project(":modules:testing"))


            }

            extensions.configure<KotlinMultiplatformExtension> {

                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(project(":modules:data"))

                            implementation(project(":modules:model"))
                            implementation(project(":modules:ui"))

                            implementation(project(":modules:designsystem"))
                            implementation(project(":modules:analytics"))
                        }

                    }
                    getByName("commonTest") {
                        this.dependencies {

                        }

                    }
                    getByName("androidMain") {
                        this.dependencies {

                        }

                    }
                    getByName("androidInstrumentedTest") {
                        this.dependencies {
//
                        }

                    }
                    getByName("jvmMain") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("jvmTest") {
                        this.dependencies {

                        }

                    }
                }

            }

        }
    }
}
