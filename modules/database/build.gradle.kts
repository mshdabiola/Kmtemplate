
plugins {
    id("mshdabiola.android.library")
//    id("mshdabiola.android.room")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)



}
android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}
dependencies {
    //add("implementation", libs.findLibrary("room.runtime").get())
    //add("implementation", libs.findLibrary("room.ktx").get())
    //add("implementation", libs.findLibrary("room.paging").get())
    //add("ksp", libs.findLibrary("room.compiler").get())
    // add("kspAndroid", libs.findLibrary("room.compiler").get())
    add("ksp", libs.room.compiler)


}
kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }
        commonMain.dependencies {
            implementation(projects.modules.model)
        }
        wasmJsMain.dependencies{
        }
        jvmTest.dependencies {
            implementation(projects.modules.testing)
        }

        val nonJsMain by getting {
            dependencies {
                implementation(libs.room.runtime)
//                implementation(libs.room.ktx)
                implementation(libs.sqlite.bundled)
            }
        }
    }
}
//configurations.commonMainApi {
//            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
//        }