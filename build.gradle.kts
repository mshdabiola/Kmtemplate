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
    theme.set(
        Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        ),
    )

    graph(
        readmePath = "${rootDir}/features/main/README.md",
        heading = "### Main Module Graph",
    ) {


        showFullPath = false
        this.focusedModulesRegex = ".*(features:main).*"
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }
    graph(
        readmePath = "${rootDir}/features/detail/README.md",
        heading = "### Detail Module Graph",
    ) {
        this.focusedModulesRegex = ".*(features:detail).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }
    graph(
        readmePath = "${rootDir}/features/setting/README.md",
        heading = "### Setting Module Graph",
    ) {
        this.focusedModulesRegex = ".*(features:setting).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/analytics/README.md",
        heading = "### Analytics Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:analytics).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/data/README.md",
        heading = "### Data Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:data).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }
    graph(
        readmePath = "${rootDir}/modules/database/README.md",
        heading = "### Database Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:database).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/datastore/README.md",
        heading = "### DataStore Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:datastore).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/domain/README.md",
        heading = "### Domain Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:domain).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }
    graph(
        readmePath = "${rootDir}/modules/model/README.md",
        heading = "### Model Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:model).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/network/README.md",
        heading = "### Network Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:network).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }
    graph(
        readmePath = "${rootDir}/modules/ui/README.md",
        heading = "### UI Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:ui).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/testing/README.md",
        heading = "### Testing Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:testing).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }

    graph(
        readmePath = "${rootDir}/modules/designsystem/README.md",
        heading = "### Design System Module Graph",
    ) {
        this.focusedModulesRegex = ".*(modules:designsystem).*"
        showFullPath = false
        this.theme = Theme.BASE(
            mapOf(
                "primaryTextColor" to "#fff",
                "primaryColor" to "#5a4f7c",
                "primaryBorderColor" to "#5a4f7c",
                "lineColor" to "#f5a623",
                "tertiaryColor" to "#40375c",
                "fontSize" to "12px",
            ),
            focusColor = "#FA8140",
        )
    }


}