import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")


}
android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}

configurations.commonMainApi {
            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
        }