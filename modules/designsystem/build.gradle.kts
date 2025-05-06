/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.designsystem"
}

dependencies {
    lintPublish(projects.lint)

    debugApi(compose.uiTooling)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.components.resources)
                api(compose.material3AdaptiveNavigationSuite)
                api(compose.components.uiToolingPreview)
                api(libs.kotlinx.collection.immutable)
                api(libs.lifecycle.runtime.compose)
//                implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")
                implementation(project(":modules:model"))
                api(libs.androidx.navigation.compose.get())

                api(libs.koin.compose)
                api(libs.koin.composeVM)

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