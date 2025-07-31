// build-logic/convention/src/main/kotlin/com/mshdabiola/app/RenameProjectArtifactsTask.kt
package com.mshdabiola.app // Or your preferred package for build logic

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

abstract class RenameProjectArtifactsTask : DefaultTask() {

    @get:Input
    @get:Option(option = "newPackageName", description = "The new package name (e.g., com.example.newapp)")
    abstract val newPackageName: Property<String>



    @get:Input
    @get:Option(option = "newPrefix", description = "The new file/class prefix (e.g., nca)")
    abstract val newPrefix: Property<String>



    @TaskAction
    fun execute() {
        val newPkg = newPackageName.get()
        val newApp = newPkg.split(".").last()
        val newPfx = newPrefix.get()
        // Ensure these functions correctly handle the case where they might not find the values
        val oldPkg = extractPackageFromAppBuildGradle(rootProject = project.rootProject) ?: ""
        val oldApp = extractAppNameFromStringsXml(rootProject = project.rootProject) ?: ""
        val oldPfx =
            extractPrefixFromApplicationKt(rootProject = project.rootProject, currentPackageName = oldPkg) ?: ""

        logger.lifecycle("Starting project artifact renaming...")
        logger.lifecycle("Old Package: $oldPkg -> New Package: $newPkg")
        logger.lifecycle("Old App Name: $oldApp -> New App Name: $newApp")
        logger.lifecycle("Old Prefix: $oldPfx -> New Prefix: $newPfx")

        val oldPkgPath = oldPkg.replace('.', File.separatorChar)
        val newPkgPath = newPkg.replace('.', File.separatorChar)

        val packageRelevantSourceRoots = listOf(
            "src/commonMain/kotlin", "src/androidMain/kotlin", "src/androidTest/kotlin",
            "src/androidUnitTest/kotlin", "src/iosMain/kotlin", "src/iosTest/kotlin",
            "src/desktopMain/kotlin", "src/jvmMain/kotlin", "src/jvmTest/kotlin",
            "src/main/kotlin", "src/main/java", "src/test/kotlin", "src/test/java", "src",
        )

        // --- 1. Rename Directories (Package Structure) ---
        logger.lifecycle("Phase 1: Renaming Directories")
        project.allprojects.forEach { proj ->
            logger.lifecycle("Checking for directory renames in project: ${proj.path}")
            packageRelevantSourceRoots.forEach { srcRootRelativePath ->
                val baseSrcDir = proj.projectDir.resolve(srcRootRelativePath)
                if (baseSrcDir.exists() && baseSrcDir.isDirectory) {
                    val oldPackageDir = baseSrcDir.resolve(oldPkgPath)
                    if (oldPkgPath.isNotEmpty() && oldPackageDir.exists() && oldPackageDir.isDirectory) {
                        val newPackageDir = baseSrcDir.resolve(newPkgPath)
                        if (oldPackageDir.path != newPackageDir.path) {
                            if (newPackageDir.exists()) {
                                logger.warn("WARNING: Target package directory ${newPackageDir.path} already exists. Manual merge might be needed.")
                            } else {
                                newPackageDir.parentFile.mkdirs()
                                logger.lifecycle("Attempting to rename directory: ${oldPackageDir.path} to ${newPackageDir.path}")
                                if (oldPackageDir.renameTo(newPackageDir)) {
                                    logger.lifecycle("SUCCESS: Renamed directory ${oldPackageDir.name} to ${newPackageDir.name} in ${proj.path}")
                                } else {
                                    logger.error("ERROR: Failed to rename directory ${oldPackageDir.path} in ${proj.path}")
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- Process files in all modules AND in the root project directory ---
        logger.lifecycle("Phase 2 & 3: Updating File Contents and Renaming Files (Modules & Root)")

        // Combine module processing and root directory processing
        val projectsToScan = project.rootProject.allprojects + project.rootProject // Add rootProject itself

        projectsToScan.forEach { currentProject -> // currentProject can be a sub-project or the rootProject
            val projectDir = currentProject.projectDir
            val logPrefix =
                if (currentProject == project.rootProject) "root project" else "project ${currentProject.path}"

            logger.lifecycle("Scanning for file updates/renames in $logPrefix directory: ${projectDir.path}")

            val filesToProcess: ConfigurableFileTree = project.fileTree(projectDir) {
                exclude(
                    "**/build/**",
                    "**/.gradle/**",
                    "**/.idea/**",
                    "**/.git/**",
                    ".github/**", // Example: GitHub Actions workflows
                    "gradle/**", // Gradle wrapper files
                    "**/*.bin", "**/*.jar", // etc.
                )

                // Common Excludes for both root (if not already excluded) and modules
                exclude(
                    // These are generally good excludes for any directory scan
                    "**/build/**", "**/.gradle/**", "**/.idea/**", "**/.git/**",
                    "**/.vs/**", "**/.vscode/**", "**/.DS_Store", "**/*.iml",
                    "**/local.properties",
                    "**/*.a", "**/*.so", "**/*.dylib", "**/*.framework/**",
                    "**/*.jar", "**/*.aar", "**/*.class", "**/*.dex",
                    "**/*.zip", "**/*.gz", "**/*.tar", "**/*.rar",
                    "**/*.png", "**/*.jpg", "**/*.jpeg", "**/*.gif", "**/*.svg",
                    "**/*.mp3", "**/*.mp4", "**/*.wav", "**/*.ogg",
                    "**/Pods/**", "**/Carthage/**", "**/node_modules/**",
                )
            }

            filesToProcess.forEach fileProcess@ { file ->


                var content: String
                try {
                    if (file.length() > 20 * 1024 * 1024) {
                        logger.debug("Skipping potentially large/binary file: ${file.path}")
                        return@fileProcess
                    }
                    content = file.readText()
                } catch (e: Exception) {
                    logger.debug("Skipping non-text or unreadable file: ${file.path} (Reason: ${e.message})")
                    return@fileProcess
                }

                var modified = false
                val originalContent = content


                // 1. Package Name (Kotlin/Java files primarily, also check build files)
                // Package declarations
                if (content.contains(oldPkg)) {
                    content = content.replace(oldPkg, newPkg)
                    modified = true
                }




                // 2. App Name
                if (content.contains(oldApp)) {
                    content = content.replace(oldApp, newApp)
                    modified = true
                }

                if (content.contains(oldApp.lowercase())) {
                    content = content.replace(
                        oldApp.lowercase(),
                        newApp.lowercase())
                    modified = true
                }


                // 3. Prefix (Class names, file names - file renaming is separate, this is for content)
                if (oldPfx.isNotEmpty()) {


                    val oldClassNamePattern = Regex("\\b${Regex.escape(
                        oldPfx.replaceFirstChar { it.uppercase() })}")
                    if (content.contains(oldClassNamePattern)) {
                        content = content.replace(oldClassNamePattern,
                            newPfx.replaceFirstChar { it.uppercase() })
                        modified = true
                    }
                    val oldLowerPrefixPattern = Regex("\\b${Regex.escape(oldPfx)}")
                    if (content.contains(oldLowerPrefixPattern)) {
                        content = content.replace(oldLowerPrefixPattern, newPfx)
                        modified = true
                    }

                }


                if (modified) {
                    logger.lifecycle("Updating content in: ${file.path} (from $logPrefix)")
                    try {
                        file.writeText(content)
                        logger.lifecycle("SUCCESS: Updated content in ${file.name} (from $logPrefix)")
                        logger.info(
                            "MODIFIED (dry run) $logPrefix: ${file.path}\n--- OLD ---\n${
                                originalContent.take(
                                    200,
                                )
                            }\n--- NEW ---\n${content.take(200)}\n----------",
                        )
                    } catch (e: Exception) {
                        logger.error("ERROR writing file content ${file.path}: ${e.message}")
                    }
                }

                // --- Rename files based on prefix (only for modules, not typically root files) ---
                if (currentProject != project.rootProject && oldPfx.isNotEmpty() &&
                    (file.nameWithoutExtension.startsWith(
                        oldPfx.replaceFirstChar { it.uppercase() }) || file.nameWithoutExtension.startsWith(
                        oldPfx,
                    ))
                ) {
                    val oldFileName = file.name
                    var newFileName = oldFileName

                    if (oldFileName.startsWith(oldPfx.replaceFirstChar { it.uppercase() })) {
                        newFileName = newFileName.replaceFirst(
                            oldPfx.replaceFirstChar { it.uppercase() },
                            newPfx.replaceFirstChar { it.uppercase() })
                    } else if (oldFileName.startsWith(oldPfx)) {
                        newFileName = newFileName.replaceFirst(oldPfx, newPfx)
                    }

                    if (oldFileName != newFileName) {
                        val newFile = File(file.parentFile, newFileName)
                        logger.lifecycle("Attempting to rename file in $logPrefix: ${file.path} to ${newFile.path}")
                        if (file.renameTo(newFile)) { // UNCOMMENT FOR ACTUAL MODIFICATION
                            logger.lifecycle("SUCCESS: Renamed file ${file.name} to ${newFile.name} in $logPrefix")
                        } else {
                            logger.error("ERROR: Failed to rename file ${file.path} in $logPrefix")
                        }
                        logger.info("RENAMED (dry run) $logPrefix: ${file.path} -> ${newFile.path}")
                    }
                }
            }
        }


        logger.lifecycle("Renaming process finished.")
        logger.lifecycle("--------------------------------------------------------------------")
        logger.lifecycle("IMPORTANT MANUAL STEPS REQUIRED:")
        logger.lifecycle("- Review ALL changes carefully using your version control (git diff).")
        logger.lifecycle("- If module names were changed in settings.gradle.kts (and their corresponding include statements), RENAME THE ACTUAL MODULE DIRECTORIES accordingly.")
        logger.lifecycle("- In Android Studio/IntelliJ: File -> Invalidate Caches / Restart.")
        logger.lifecycle("- Perform a clean build: ./gradlew clean build")
        logger.lifecycle("--------------------------------------------------------------------")
    }

    // --- Helper functions to extract old values (implement these robustly) ---
    private fun extractPackageFromAppBuildGradle(rootProject: Project): String? {
        val appProject = rootProject.allprojects.find { it.name == "app" } // Or your main app module name
        val buildFile = appProject?.file("build.gradle.kts")
        if (buildFile?.exists() == true) {
            val content = buildFile.readText()
            // Try namespace first, then applicationId
            val namespacePattern = Regex("""namespace\s*=\s*"([^"]+)"""")
            namespacePattern.find(content)?.groupValues?.get(1)?.let { return it }

            val appIdPattern = Regex("""applicationId\s*=\s*"([^"]+)"""")
            appIdPattern.find(content)?.groupValues?.get(1)?.let { return it }
        }
        logger.warn("Could not automatically determine old package name from app's build.gradle.kts.")
        return null // Or a default/fallback if appropriate
    }

    private fun extractAppNameFromStringsXml(rootProject: Project): String? {
        // Search in common places for strings.xml
        val possibleStringsFiles = listOfNotNull(
            rootProject.allprojects.find { it.name == "app" }?.file("src/main/res/values/strings.xml"),
            rootProject.allprojects.find { it.name == "app" }
                ?.file("src/androidMain/res/values/strings.xml"), // KMP Android
            rootProject.projectDir.resolve("app/src/main/res/values/strings.xml"), // If task is run from root
        )
        val stringsFile = possibleStringsFiles.find { it.exists() }

        if (stringsFile?.exists() == true) {
            val content = stringsFile.readText()
            val appNamePattern = Regex("""<string name="app_name">([^<]+)</string>""")
            return appNamePattern.find(content)?.groupValues?.get(1)
        }
        logger.warn("Could not automatically determine old app name from strings.xml.")
        return null
    }

    private fun extractPrefixFromApplicationKt(rootProject: Project, currentPackageName: String): String? {
        if (currentPackageName.isEmpty()) {
            logger.warn("Cannot extract prefix: current package name is unknown.")
            return null
        }

        val appModule = rootProject.allprojects.find { it.name == "app" } // Assuming your app module is named 'app'
        if (appModule == null) {
            logger.warn("Cannot extract prefix: 'app' module not found.")
            return null
        }

        // Construct the expected path to the Application class
        // Adjust "androidMain" if your KMP structure for the Application class is different
        // Adjust "NdaApplication.kt" if your Application class has a different common name pattern
        val packageAsPath = currentPackageName.replace('.', File.separatorChar)
        val expectedAppFilePathParts = listOf(
            "src", "androidMain", "kotlin", packageAsPath, "KmtApplication.kt" // Specific to your example
            // Add more common Application class names if needed:
            // "src", "androidMain", "kotlin", packageAsPath, "MainApplication.kt"
            // "src", "androidMain", "kotlin", packageAsPath, "App.kt"
        )
        // Resolve the file path within the app module
        val applicationFile = appModule.projectDir.resolve(expectedAppFilePathParts.joinToString(File.separator))

        if (applicationFile.exists() && applicationFile.isFile) {
            try {
                val content = applicationFile.readText()
                // Regex to find "class NdaApplication" (or similar) and capture "Nda"
                // This regex assumes:
                // - The class name starts with an uppercase prefix (e.g., "Nda").
                // - The prefix is followed by "Application".
                // - It captures the part before "Application".
                // \b ensures word boundaries to avoid partial matches.
                val classNamePattern = Regex("""\bclass\s+([A-Z][a-zA-Z0-9_]*?)Application\b""")
                val match = classNamePattern.find(content)

                if (match != null && match.groupValues.size > 1) {
                    val potentialPrefix = match.groupValues[1] // The captured group
                    if (potentialPrefix.isNotBlank()) {
                        logger.lifecycle("Extracted prefix '$potentialPrefix' from ${applicationFile.path}")
                        return potentialPrefix.lowercase()
                    }
                } else {
                    logger.warn("Could not find a class name matching the pattern 'PrefixApplication' in ${applicationFile.path}")
                }
            } catch (e: Exception) {
                logger.error("Error reading or parsing ${applicationFile.path}: ${e.message}")
            }
        } else {
            logger.warn("Application file not found at expected path: ${applicationFile.path}")
            // Fallback: You could try searching more broadly if the specific path fails,
            // but that makes the logic more complex and potentially less reliable.
            // For now, we'll stick to the expected path.
        }

        logger.warn("Could not automatically determine old class prefix from Application class.")
        return null // Or a default/fallback if appropriate
    }
}

