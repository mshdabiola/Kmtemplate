
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.AbstractExecTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction

abstract class SarifMergeTask : AbstractExecTask<SarifMergeTask>(SarifMergeTask::class.java) {

    @InputFiles
    @SkipWhenEmpty
    val inputSarifFiles: ConfigurableFileCollection = project.objects.fileCollection()

    @OutputFile
    val outputSarifFile: RegularFileProperty = project.objects.fileProperty()

    init {
        outputSarifFile.set(project.layout.buildDirectory.file("lint-merged.sarif"))
        executable = "npx"
    }

    @TaskAction
    override fun exec() {
        val argsList = listOf(
            "@microsoft/sarif-multitool",
            "merge",
            "--force",
            "--merge-runs",
            "--recurse",
            "--output-file",
            outputSarifFile.get().asFile.absolutePath,
        ) + inputSarifFiles.map { it.absolutePath }
        args(argsList)
        super.exec()
    }
}
