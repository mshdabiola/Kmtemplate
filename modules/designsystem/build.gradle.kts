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
                api(libs.kotlinx.collection.immutable)
               implementation(project(":modules:model"))
                api(libs.androidx.compose.material3.windowSizeClass2)
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

//configurations.all {
//    resolutionStrategy.eachDependency {
//        if (
//            requested.group.startsWith("org.jetbrains.compose.runtime") ||
//            requested.group.startsWith("org.jetbrains.compose.ui") ||
//            requested.group.startsWith("org.jetbrains.compose.foundation") ||
//            requested.group.startsWith("org.jetbrains.compose.material") ||
//            requested.group.startsWith("org.jetbrains.compose.material3")
//        ) {
//            useVersion(libs.versions.compose.plugin.get())
//        }
//    }
//}