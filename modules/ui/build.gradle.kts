/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
    alias(libs.plugins.screenshot)

//    id("mshdabiola.android.library.jacoco")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.ui"
    experimentalProperties["android.experimental.enableScreenshotTest"] = true

}

dependencies {
    api(libs.androidx.metrics)
    implementation(libs.androidx.ui.text.google.fonts)

    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.compose.ui.testManifest)

    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(projects.modules.testing)
    testImplementation(projects.modules.screenshotTesting)


    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(projects.modules.testing)

    screenshotTestImplementation(libs.androidx.compose.ui.tooling)

}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":modules:analytics"))
                implementation(project(":modules:designsystem"))
                implementation(project(":modules:model"))
                api(libs.coil.kt)
                api(libs.coil.kt.compose)
                api(libs.coil.kt.svg)
                api(libs.coil.kt.network)

                api(compose.components.resources)


            }
        }
    }
}
