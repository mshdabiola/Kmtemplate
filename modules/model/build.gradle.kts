@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.model"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)

            }
        }

    }
}