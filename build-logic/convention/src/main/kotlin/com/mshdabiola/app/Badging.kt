/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.app

import com.android.SdkConstants
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.process.ExecOperations
import java.io.File
import java.util.Locale
import javax.inject.Inject
import kotlin.math.max

@CacheableTask
abstract class GenerateBadgingTask : DefaultTask() {

    @get:OutputFile
    abstract val badging: RegularFileProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val apk: RegularFileProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val aapt2Executable: RegularFileProperty

    @get:Inject
    abstract val execOperations: ExecOperations

    @TaskAction
    fun taskAction() {
        execOperations.exec {
            commandLine(
                aapt2Executable.get().asFile.absolutePath,
                "dump",
                "badging",
                apk.get().asFile.absolutePath,
            )
            standardOutput = badging.asFile.get().outputStream()
        }
    }
}

@CacheableTask
abstract class CheckBadgingTask : DefaultTask() {

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val goldenBadging: RegularFileProperty

    @get:PathSensitive(PathSensitivity.NONE)
    @get:InputFile
    abstract val generatedBadging: RegularFileProperty

    @get:Input
    abstract val updateBadgingTaskName: Property<String>

    override fun getGroup(): String = LifecycleBasePlugin.VERIFICATION_GROUP

    @TaskAction
    fun taskAction() {
        val goldenFile = goldenBadging.get().asFile
        val generatedFile = generatedBadging.get().asFile

        val goldenText = goldenFile.readText()
        val generatedText = generatedFile.readText()

        if (goldenText == generatedText) {
            return // Files are identical, task is successful.
        }

        // Files differ, generate a diff for the error message.
        val goldenLines = goldenText.lines()
        val generatedLines = generatedText.lines()

        val diffOutput = StringBuilder()
        diffOutput.appendLine(
            "Generated badging is different from golden badging! " +
                "If this change is intended, run ./gradlew ${updateBadgingTaskName.get()}",
        )
        diffOutput.appendLine("--- Diff ---")

        val maxLines = max(goldenLines.size, generatedLines.size)
        for (i in 0 until maxLines) {
            val goldenLine = goldenLines.getOrNull(i)
            val generatedLine = generatedLines.getOrNull(i)

            when {
                goldenLine == generatedLine -> {
                    // Lines are same, do nothing for a concise diff.
                }
                goldenLine != null && generatedLine == null -> {
                    diffOutput.appendLine("- $goldenLine") // Line removed
                }
                goldenLine == null && generatedLine != null -> {
                    diffOutput.appendLine("+ $generatedLine") // Line added
                }
                else -> { // Both are non-null and different
                    diffOutput.appendLine("- $goldenLine")
                    diffOutput.appendLine("+ $generatedLine")
                }
            }
        }

        throw AssertionError(diffOutput.toString())
        }
    }



fun Project.configureBadgingTasks(
    baseExtension: BaseExtension,
    componentsExtension: ApplicationAndroidComponentsExtension,
) {
    // Registers a callback to be called, when a new variant is configured
    componentsExtension.onVariants { variant ->
        // Registers a new task to verify the app bundle.
        val capitalizedVariantName = variant.name.let<CharSequence, CharSequence> {
            if (it.isEmpty()) it else it[0].titlecase(Locale.getDefault()) + it.substring(1)
        }
        val generateBadgingTaskName = "generate${capitalizedVariantName}Badging"
        val generateBadging =
            tasks.register<GenerateBadgingTask>(generateBadgingTaskName) {
                apk.set(
                    variant.artifacts.get(SingleArtifact.APK_FROM_BUNDLE),
                )
                aapt2Executable.set(
                    File(
                        baseExtension.sdkDirectory,
                        "${SdkConstants.FD_BUILD_TOOLS}/" +
                            "${baseExtension.buildToolsVersion}/" +
                            SdkConstants.FN_AAPT2,
                    ),
                )

                badging.set(
                    project.layout.buildDirectory.file(
                        "outputs/apk_from_bundle/${variant.name}/${variant.name}-badging.txt",
                    ),
                )
            }

        val updateBadgingTaskName = "update${capitalizedVariantName}Badging"
        tasks.register<Copy>(updateBadgingTaskName) {
            from(generateBadging.get().badging)
            into(project.layout.projectDirectory)
        }

        val checkBadgingTaskName = "check${capitalizedVariantName}Badging"
        tasks.register<CheckBadgingTask>(checkBadgingTaskName) {
            goldenBadging.set(
                project.layout.projectDirectory.file("${variant.name}-badging.txt"),
            )
            generatedBadging.set(
                generateBadging.get().badging,
            )
            this.updateBadgingTaskName.set(updateBadgingTaskName)

            output.set(
                project.layout.buildDirectory.dir("intermediates/$checkBadgingTaskName"),
            )
        }
    }
}
