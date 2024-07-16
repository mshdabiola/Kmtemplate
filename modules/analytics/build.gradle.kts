/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

}

android {
    namespace = "com.mshdabiola.analytics"
}

dependencies {
    implementation(compose.runtime)
    googlePlayImplementation(platform(libs.firebase.bom))
    googlePlayImplementation(libs.firebase.analytics)
}
kotlin {

    sourceSets {


        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)


            }
        }
    }
}