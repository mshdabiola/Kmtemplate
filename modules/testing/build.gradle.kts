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

    debugApi(libs.androidx.compose.ui.testManifest)
    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(libs.roborazzi)

    api(project(":modules:analytics"))
    api(project(":modules:data"))
    api(project(":modules:model"))


    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(libs.accompanist.testharness)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.test.rules)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(libs.robolectric.shadows)
    implementation(project(":modules:designsystem"))
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                kotlin("test")
                //    implementation(project(":core:common"))
//                implementation(project(":modules:data"))
//                implementation(project(":modules:model"))
                api(libs.junit)
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
        val androidInstrumentedTest by getting {


        }


        val desktopMain by getting {
            dependencies {

            }
        }

        val desktopTest by getting

//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}