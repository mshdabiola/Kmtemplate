

import com.diffplug.gradle.spotless.SpotlessExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.mshdabiola.app.configureKotlinJvm
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.diffplug.spotless")

            }

            extensions.configure<SpotlessExtension> {
                val ktlintVersion =libs.findLibrary("ktlint.cli").get().get().version

                kotlin {
                    target("src/**/*.kt")
                    ktlint(ktlintVersion)
                    licenseHeaderFile(rootProject.file("spotless/copyright.kt")).updateYearWithLatest(true)
                }

                kotlinGradle {
                    target("*.gradle.kts")
                    ktlint(ktlintVersion)
                    licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
                        .updateYearWithLatest(true)
                }

            }
        }
    }
}
