@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.network"
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                // implementation("ch.qos.logback:logback-classic:1.4.7")

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.resources)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.ktor.client.mock)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.logging)
            }
        }


        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.logging)

                implementation(libs.ktor.client.cio)
            }
        }

//        val wasmJsMain by getting {
//            dependencies {
//                implementation(libs.ktor.client.js)
//            }
//        }



//        val jsMain by getting {
//            dependencies {
//
//            }
//        }

//        val test by getting{
//
//        }
    }
}
//
//dependencies {
//
//    testImplementation(project(":core:testing"))
//    implementation(libs.paging.runtime)
//    implementation(libs.paging.common)
//}
//task("testClasses")
