import com.mshdabiola.app.BuildType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)

    id("mshdabiola.android.application")
    id("mshdabiola.android.application.compose")
    id("mshdabiola.android.application.jacoco")
    id("mshdabiola.android.application.flavor")
    alias(libs.plugins.conveyor)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.roborazzi)

}

group = "com.mshdabiola.skeletonapp"
version = "1.1.3"

dependencies {

//    implementation(project(":modules:app"))
//    implementation(project(":modules:model"))
//    implementation(project(":modules:data"))
//    implementation(project(":modules:navigation"))
//    implementation(project(":modules:analytics"))
//    implementation(project(":modules:mvvn"))
//    implementation(project(":modules:designsystem"))
//

    linuxAmd64(compose.desktop.linux_x64)
    macAmd64(compose.desktop.macos_x64)
    macAarch64(compose.desktop.macos_arm64)
    windowsAmd64(compose.desktop.windows_x64)

    implementation(libs.koin.android)



    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
//    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.window.core)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)


    debugImplementation(libs.androidx.compose.ui.testManifest)


    testImplementation(projects.modules.testing)
    testImplementation(libs.androidx.compose.ui.test)
    testImplementation(libs.androidx.work.testing)

    testFossReliantImplementation(libs.robolectric)
    testFossReliantImplementation(libs.roborazzi)
    //testFossReliantImplementation(projects.modules.screenshotTesting)


    androidTestImplementation(projects.modules.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.compose.ui.test)

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
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    // jvm("desktop")
    jvm()
    // jvmToolchain(17)

    sourceSets {
        val jvmMain by getting

        androidMain.dependencies {
            //implementation(libs.compose.ui.tooling.preview)
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
            implementation(libs.androidx.compose.material3.adaptive)


            implementation(projects.features.main)
            implementation(projects.features.detail)
            implementation(projects.features.setting)





            // Logger
            implementation(libs.kermit)

            implementation(libs.kermit.koin)


        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            // implementation(project(":modules:app"))

            implementation(libs.kotlinx.coroutines.swing)

        }
        targets.all {
            compilations.all {
                compilerOptions.configure {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
//        configurations.commonMainApi {
//            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
//        }

    }
}

android {

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")

    namespace = "com.mshdabiola.skeletonapp"

    defaultConfig {
        applicationId = "com.mshdabiola.skeletonapp"
        versionCode = 3
        versionName = "1.1.3" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.mshdabiola.testing.TestRunner"
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
             signingConfig = signingConfigs.getByName("debug")
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
        mainClass = "com.mshdabiola.skeletonapp.MainAppKt"


        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(true)
            version.set("7.4.2")
        }

//        val iconsRoot = project.file("src/desktopMain/resources/launcher")
//        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
//            packageVersion = "1.0.1"
//            packageName = "Skeleton"
//            description = "Template"
//            copyright = "© 2022 Mshdabiola. All rights reserved."
//            vendor = "Mshdabiola App"
//            version = "1.0.1"
//            licenseFile.set(rootProject.file("LICENSE"))
//
//            modules("java.net.http", "java.sql")
//
//            linux {
//                iconFile.set(iconsRoot.resolve("linux.png"))
//                debMaintainer = "mshdabiola@gmail.com"
//                menuGroup = packageName
//                appCategory = "Productivity"
//            }
//
//            windows {
//                iconFile.set(iconsRoot.resolve("windows.ico"))
//                shortcut = true
//                menuGroup = packageName
////                https://www.guidgen.com/
//
//                //https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
//                upgradeUuid = "791AC64E-C9A7-4CBF-A1C4-AFE5CFFDDDFA"
//            }
//
//            macOS {
//                iconFile.set(iconsRoot.resolve("macos.icns"))
//                bundleID = "com.mshdabiola.skeleton"
//                appCategory = "public.app-category.productivity"
//                signing {
//                    sign.set(false)
//                }
//            }
//        }
    }


}

configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}

//compose.experimental {
//    web.application {}
//}


baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}

dependencyGuard {
    configuration("fossReliantReleaseRuntimeClasspath")
    configuration("googlePlayDebugRuntimeClasspath")

}
