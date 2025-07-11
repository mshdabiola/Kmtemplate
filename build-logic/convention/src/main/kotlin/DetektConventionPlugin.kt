import com.mshdabiola.app.libs
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("io.gitlab.arturbosch.detekt")

        extensions.configure<DetektExtension> {
            val rootDetektConfig = target.rootProject.file("detekt.yml")
            val localDetektConfig = target.file("detekt.yml")

            val rootDetektComposeConfig = target.rootProject.file("detekt-compose.yml")
            if (localDetektConfig.exists()) {
                config.from(localDetektConfig, rootDetektConfig,rootDetektComposeConfig)
            } else {
                config.from(rootDetektConfig, rootDetektComposeConfig)
            }
//            config.setFrom(files("$rootDir/detekt.yml"))
            buildUponDefaultConfig = true
            parallel = true
            ignoreFailures = true
            basePath = rootProject.projectDir.absolutePath
        }
        dependencies {
                add("detektPlugins", libs.findLibrary("detekt-compose").get())
        }
        val reportMerge by tasks.registering(ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.sarif"))
        }
        tasks.withType<Detekt>().configureEach {
            jvmTarget = "21"
            exclude("**/build/**")
            finalizedBy(reportMerge)
        }


        reportMerge.configure {
            input.from(tasks.withType<Detekt>().map { it.sarifReportFile })
        }
    }
}
