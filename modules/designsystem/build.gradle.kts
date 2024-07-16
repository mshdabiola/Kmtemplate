/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
    id("mshdabiola.android.library.jacoco")

}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.designsystem"
}

dependencies {

//    api(libs.androidx.compose.material3.adaptive)
//    api(libs.androidx.compose.material3.navigationSuite)


//    debugApi(libs.androidx.compose.ui.tooling)
    debugApi(compose.uiTooling)
    api(compose.preview)

//    implementation(libs.coil.kt.compose)

//    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(projects.modules.testing)


    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(projects.modules.testing)

}
kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(libs.kotlinx.collection.immutable)
               implementation(project(":modules:model"))
                api(libs.androidx.compose.material3.windowSizeClass2)
                api(libs.androidx.navigation.compose)

                api(libs.koin.compose)
                api(libs.koin.composeVM)
                api(libs.androidx.lifecycle.viewModelCompose)

            }
        }
        val androidMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.androidx.lifecycle.runtimeCompose)
                api(libs.androidx.lifecycle.viewModelCompose)
                implementation(libs.androidx.ui.text.google.fonts)

            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.kotlinx.coroutines.swing)
            }
        }


    }
}
task("testClasses")
