

import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.google.devtools.ksp")
                apply("androidx.room")

            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")

            }
            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }


            dependencies {
                //add("implementation", libs.findLibrary("room.runtime").get())
                //add("implementation", libs.findLibrary("room.ktx").get())
                //add("implementation", libs.findLibrary("room.paging").get())
                //add("ksp", libs.findLibrary("room.compiler").get())
                // add("kspAndroid", libs.findLibrary("room.compiler").get())
                add("ksp", libs.findLibrary("room.compiler").get())


            }
//            extensions.configure<RoomExtension>{
//                schemaDirectory("$projectDir/schemas")
//
//
//            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                jvm()
                jvmToolchain(21)
                // val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    commonMain.dependencies {
                        implementation(project(":modules:model"))
                        implementation(libs.findLibrary("room.runtime").get())
                        implementation(libs.findLibrary("room.ktx").get())
                        implementation(libs.findLibrary("room.paging").get())
//                            implementation(libs.findLibrary("paging.common").get())

                        api(libs.findLibrary("sqlite.bundled").get())//sqlite-bundled

                    }
                    jvmTest.dependencies {
                        implementation(project(":modules:testing"))
                    }

                }

            }
        }
    }


}