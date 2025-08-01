/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.mshdabiola.app.BuildType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)

    id("mshdabiola.android.application")
    id("mshdabiola.android.application.compose")
    id("mshdabiola.android.application.flavor")
    alias(libs.plugins.conveyor)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.composehot)
    alias(libs.plugins.screenshot)

}

group = "com.mshdabiola.kmtemplate"
version = libs.versions.versionName.get()

dependencies {
    linuxAmd64(compose.desktop.linux_x64)
    macAmd64(compose.desktop.macos_x64)
    macAarch64(compose.desktop.macos_arm64)
    windowsAmd64(compose.desktop.windows_x64)

    implementation(libs.koin.android)



    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.kotlinx.coroutines.guava)

    debugImplementation(libs.androidx.compose.ui.testManifest)


    androidTestImplementation(projects.modules.testing)
    androidTestImplementation(libs.androidx.navigation.testing)

    baselineProfile(projects.benchmarks)


    googlePlayImplementation(platform(libs.firebase.bom))
    googlePlayImplementation(libs.firebase.analytics)
    googlePlayImplementation(libs.firebase.performance)
    googlePlayImplementation(libs.firebase.crashlytics)

    googlePlayImplementation(libs.firebase.cloud.messaging)
    googlePlayImplementation(libs.firebase.remoteconfig)
    googlePlayImplementation(libs.firebase.message)
    googlePlayImplementation(libs.firebase.auth)

    googlePlayImplementation(libs.play.game)
    googlePlayImplementation(libs.play.update)
    googlePlayImplementation(libs.play.update.kts)
    googlePlayImplementation(libs.play.review)
    googlePlayImplementation(libs.play.review.kts)

    implementation("ch.acra:acra-mail:5.12.0")

    screenshotTestImplementation(libs.screenshot.validation.api)
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)

}

kotlin {
    androidTarget()
    jvm()
    jvmToolchain(21)

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("nonJs") {
                withAndroidTarget()
                // withIos()
                withJvm()
            }
        }
    }

    sourceSets {
        val jvmMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)

        }
        commonMain.dependencies {
//
            implementation(libs.koin.core)

            implementation(projects.modules.designsystem)
            implementation(projects.modules.data)
            implementation(projects.modules.ui)
            implementation(projects.modules.model)
            implementation(projects.modules.analytics)


            implementation(projects.features.main)
            implementation(projects.features.detail)
            implementation(projects.features.setting)

            // Logger
            implementation(libs.kermit)
//            implementation(libs.kermit.koin)

            implementation(libs.androidx.compose.material3.adaptive)
            implementation(libs.androidx.compose.material3.adaptive.layout)
            implementation(libs.androidx.compose.material3.adaptive.navigation)
            implementation(compose.components.resources)


        }
        val nonJsMain by getting{
            dependencies {
                implementation(libs.kermit.koin)

            }
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation("io.sentry:sentry-kotlin-multiplatform-jvm:0.18.0")
        }
        jvmTest.dependencies {
            implementation(projects.modules.testing)
        }

    }
}


android {

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")

    namespace = "com.mshdabiola.kmtemplate"
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
    defaultConfig {
        applicationId = "com.mshdabiola.kmtemplate"
        versionCode = libs.versions.versionCode.get().toIntOrNull()
        versionName = System.getenv("VERSION_NAME") ?: libs.versions.versionName.get()

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        val release = getByName("release") {
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            // signingConfig = signingConfigs.getByName("debug")
            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
        create("benchmark") {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

compose.desktop {
    application {
        mainClass = "com.mshdabiola.kmtemplate.MainAppKt"


        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(true)
            version.set("7.4.2")
        }

    }


}

configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}


configurations.configureEach {
    exclude("androidx.window.core", "window-core")
}


baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}

dependencyGuard {
    configuration("fossReliantReleaseRuntimeClasspath")
    configuration("googlePlayDebugRuntimeClasspath")

}
