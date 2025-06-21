
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
        create("mihon") {
            from(files("../gradle/mshd.versions.toml"))
        }
    }
}
//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
//}

rootProject.name = "build-logic"
include(":convention")
