
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.lint.AndroidLintTask
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class LintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.withId("com.android.library") {
            configureLint(target)
        }
        target.plugins.withId("com.android.application") {
            configureLint(target)
        }
    }

    private fun configureLint(target: Project) {
        target.extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
            lint {
                sarifReport = true
                baseline = target.file("lint-baseline.xml")
                warningsAsErrors = true
            }
        }

        val rootProject = target.rootProject
rootProject.plugins.withId("appyx-collect-sarif") { // TODO: Use the correct registered plugin ID
    rootProject.tasks.named(
        CollectSarifPlugin.MERGE_LINT_TASK_NAME,
        SarifMergeTask::class.java,
    ) {
        inputSarifFiles.from(
            target.tasks
                .named("lintReportDebug", AndroidLintTask::class.java)
                .flatMap { it.sarifReportOutputFile }
        )
    }
}
        }
    }
}
