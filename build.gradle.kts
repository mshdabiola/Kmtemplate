import dev.iurysouza.modulegraph.ModuleType.AndroidApp
import dev.iurysouza.modulegraph.ModuleType.AndroidLibrary
import dev.iurysouza.modulegraph.ModuleType.Custom
import dev.iurysouza.modulegraph.Theme

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
        id("dev.iurysouza.modulegraph") version "0.12.0"

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.powerAssert) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.conveyor) apply false
    alias(libs.plugins.screenshot) apply false
    alias(libs.plugins.kover) apply false



}

moduleGraphConfig {
    heading = "###  Hydraulic Module Graph"
    readmePath = "${rootDir}/FULLGRAPH.md"
    showFullPath = true
    setStyleByModuleType.set(true)
    graph(
        readmePath = "${rootDir}/features/main/README.md",
        heading = "### Main Module Graph",
    ) {

        showFullPath = false
        this.focusedModulesRegex=".*(features:main).*"
    }

}