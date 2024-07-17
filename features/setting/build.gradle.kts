/*
 *abiola 2022
 */

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.feature")
}

android {
    namespace = "com.mshdabiola.setting"
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.components.resources)
            }
        }


    }
}
