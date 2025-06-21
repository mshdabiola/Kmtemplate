plugins {
    `kotlin-dsl`
    alias(libs.plugins.spotless)
}

group = "com.mshdabiola.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//kotlin {
//    jvmToolchain(21)
//}

// Configuration should be synced with [/build-logic/src/main/kotlin/mihon/gradle/configurations/spotless.kt]
spotless {
    val ktlintVersion = libs.ktlint.cli.get().version
    kotlin {
        target("src/**/*.kt")
        ktlint(ktlintVersion).setEditorConfigPath(rootProject.file("../.editorconfig"))
        licenseHeaderFile(rootProject.file("../spotless/copyright.kt")).updateYearWithLatest(true)
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint(ktlintVersion).setEditorConfigPath(rootProject.file("../.editorconfig"))
        licenseHeaderFile(rootProject.file("../spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
            .updateYearWithLatest(true)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.performance.gradlePlugin)
    implementation(libs.truth)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.kotlin.powerAssert)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kover.gradlePlugin)
    compileOnly(libs.compose.hot.gradlePlugin)
    compileOnly(libs.spotless.gradle)





}

gradlePlugin {
    plugins {

        register("androidApplicationCompose") {
            id = "mshdabiola.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplicationFlavor") {
            id = "mshdabiola.android.application.flavor"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("androidApplicationFirebase") {
            id = "mshdabiola.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidApplication") {
            id = "mshdabiola.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }


        register("androidLibraryCompose") {
            id = "mshdabiola.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "mshdabiola.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "mshdabiola.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidTest") {
            id = "mshdabiola.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }


        register("androidLint") {
            id = "mshdabiola.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("jvmLibrary") {
            id = "mshdabiola.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("androidRoom") {
            id = "mshdabiola.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("spotless") {
            id = mihon.plugins.spotless.get().pluginId
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}
