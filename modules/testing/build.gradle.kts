/*
 *abiola 2024
 */
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.testing"
}
dependencies {

    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(projects.modules.analytics)
    api(projects.modules.data)
    api(projects.modules.model)

    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(libs.androidx.test.rules)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(projects.modules.designsystem)
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                kotlin("test")
                //    implementation(project(":core:common"))
//                implementation(project(":modules:data"))
//                implementation(project(":modules:model"))
               // api(libs.junit)
               api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.koin.test.junit)
            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {
                //  debugApi(libs.androidx.compose.ui.testManifest)
//                api(libs.androidx.test.core)
//                api(libs.androidx.test.espresso.core)
//                api(libs.androidx.test.runner)
//                api(libs.androidx.test.rules)
//                api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }
        }

//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}