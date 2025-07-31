import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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
plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

group = "com.mshdabiola.buildlogic"


kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
    jvmToolchain(21)
}

// Configuration should be synced with [/build-logic/src/main/kotlin/mihon/gradle/configurations/spotless.kt]
spotless {

    kotlin {
        target("src/**/*.kt")
        ktlint().setEditorConfigPath(rootProject.file("../.editorconfig"))
        licenseHeaderFile(rootProject.file("../spotless/copyright.kt")).updateYearWithLatest(true)
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint().setEditorConfigPath(rootProject.file("../.editorconfig"))
        licenseHeaderFile(rootProject.file("../spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
            .updateYearWithLatest(true)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    implementation(libs.truth)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.kotlin.powerAssert)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    compileOnly(libs.compose.hot.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.jlleitschuh.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)

//    compileOnly("org.jlleitschuh.gradle:ktlint-gradle:12.3.0")






}

gradlePlugin {
    plugins {

        register("androidApplicationCompose") {
            id = "mshdabiola.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationFlavor") {
            id = "mshdabiola.android.application.flavor"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidApplicationFirebase") {
            id = "mshdabiola.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidApplication") {
            id = "mshdabiola.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }


        register("androidLibraryCompose") {
            id = "mshdabiola.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "mshdabiola.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "mshdabiola.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidTest") {
            id = "mshdabiola.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }


        register("androidLint") {
            id = "mshdabiola.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("jvmLibrary") {
            id = "mshdabiola.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidRoom") {
            id = "mshdabiola.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("spotless") {
            id = "mshdabiola.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("detekt") {
            id = "mshdabiola.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("ci.task") {
            id = "mshdabiola.ci.task"
            implementationClass = "CiTaskPlugin"
        }
        register("kover") {
            id = "mshdabiola.kover"
            implementationClass = "KoverConventionPlugin"
        }

    }
}

