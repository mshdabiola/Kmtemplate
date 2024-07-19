@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
}

android {
    namespace = "com.mshdabiola.model"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)

            }
        }

    }
}