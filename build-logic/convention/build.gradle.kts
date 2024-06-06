plugins {
    `kotlin-dsl`
}

group = "com.mshdabiola.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradle)
    compileOnly(libs.firebase.performance.gradle)
    implementation(libs.truth)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.kotlin.powerAssert)



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
        register("androidApplicationJacoco") {
            id = "mshdabiola.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidApplication") {
            id = "mshdabiola.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "mshdabiola.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
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
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}