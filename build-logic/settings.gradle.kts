
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        maven("https://plugins.gradle.org/m2/")
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }

    }
}

rootProject.name = "build-logic"
include(":convention")
