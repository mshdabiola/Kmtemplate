import com.mshdabiola.app.libs

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
kotlin{
    sourceSets {

        val nonJsMain by getting {

            dependencies {
//                api(libs.room.runtime)
//                implementation(libs.sqlite.bundled)
            }
        }
    }
}

configurations.commonMainApi {
            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
        }