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
    @get:Option(option = "newAppName", description = "The new application name (e.g., NewCoolApp)")
    abstract val newAppName: Property<String>

    @get:Input
    @get:Option(option = "newPrefix", description = "The new file/class prefix (e.g., nca)")
    abstract val newPrefix: Property<String>


    // Optional: If you want to configure specific modules to process
    // @get:Input
    // abstract val targetModules: ListProperty<String>

    @TaskAction
    fun execute() {
        val newPkg = newPackageName.get()
        val newApp = newAppName.get()
        val newPfx = newPrefix.get()
        // Ensure these functions correctly handle the case where they might not find the values
        val oldPkg = extractPackageFromAppBuildGradle(rootProject = project.rootProject) ?: ""
        val oldApp = extractAppNameFromStringsXml(rootProject = project.rootProject) ?: ""
        val oldPfx = extractPrefixFromApplicationKt(rootProject = project.rootProject, currentPackageName = oldPkg) ?: ""

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
            "src/main/kotlin", "src/main/java", "src/test/kotlin", "src/test/java", "src"
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
            val logPrefix = if (currentProject == project.rootProject) "root project" else "project ${currentProject.path}"

            logger.lifecycle("Scanning for file updates/renames in $logPrefix directory: ${projectDir.path}")

            val filesToProcess: ConfigurableFileTree = project.fileTree(projectDir) {
                // For root project, we might want different include/exclude logic
                if (currentProject == project.rootProject) {
                    // Only include specific files at the root, and don't go into sub-project directories again
                    include(
                        "README.md",
                        "LICENSE", // Example
                        "settings.gradle.kts", // Already handled later, but can be here too
                        "build.gradle.kts", // Root build.gradle.kts if you need to change anything there
                        "*.properties" // e.g. gradle.properties
                        // Add other specific root file patterns
                    )
                    // Exclude common directories that are not subprojects or that you don't want to touch
                    exclude(
                        "**/build/**",
                        "**/.gradle/**",
                        "**/.idea/**",
                        "**/.git/**",
                        ".github/**", // Example: GitHub Actions workflows
                        "gradle/**", // Gradle wrapper files
                        "**/*.bin", "**/*.jar", // etc.
                        // CRITICAL: Exclude all subproject directories to avoid double processing
                        // project.rootProject.subprojects.map { it.name + "/**" } // This can be tricky if names change
                    )


                } else { // For sub-projects (modules)
                    include(
                        "**/*.kt", "**/*.java", "**/*.xml", "**/*.gradle.kts",
                        "**/Info.plist", "**/project.pbxproj", "**/Project.swift"
                        // No settings.gradle.kts here typically
                    )
                }

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
                    "**/Pods/**", "**/Carthage/**", "**/node_modules/**"
                )
            }

            filesToProcess.forEach { file ->
                // Check if the file is directly in the projectDir (for root project case with depth = 1)
                // or if it's in a sub-directory (for modules)
                if (currentProject == project.rootProject && file.parentFile != projectDir && projectDir.resolve(file.name) != file) {
                    // This check ensures we only process files directly in root when depth is used,
                    // and not files from subprojects that might slip through exclude patterns.
                    // However, with explicit includes for root like "README.md", this might be too restrictive.
                    // The `depth = 1` for the root fileTree is a more direct way to handle this.
                    // logger.debug("Skipping ${file.path}, as it's not directly in the root project dir for root processing.")
                    // return@forEach
                }


                var content: String
                try {
                    if (file.length() > 20 * 1024 * 1024) {
                        logger.debug("Skipping potentially large/binary file: ${file.path}")
                        return@forEach
                    }
                    content = file.readText()
                } catch (e: Exception) {
                    logger.debug("Skipping non-text or unreadable file: ${file.path} (Reason: ${e.message})")
                    return@forEach
                }

                var modified = false
                val originalContent = content

                // --- Apply Replacements ---
                // (Your existing replacement logic: package, app name, prefix, AndroidManifest, build.gradle.kts)
                // Ensure this logic is safe to run on all matched file types.

                // 1. Package Name (Kotlin/Java files primarily, also check build files)
                if (file.extension == "kt" || file.extension == "java" || file.name.endsWith(".gradle.kts")) {
                    if (oldPkg.isNotEmpty() && content.contains(oldPkg)) { // Make this more specific if needed
                        // Package declarations
                        if (content.contains("package $oldPkg")) {
                            content = content.replace("package $oldPkg", "package $newPkg")
                            modified = true
                        }
                        // Imports
                        content = content.replace(Regex("import\\s+${Regex.escape(oldPkg)}(\\.\\*|\\.[A-Za-z0-9_]+(?:\\.[A-Za-z0-9_]+)*(\\.\\*)?)"), "import $newPkg$1")
                        // Namespace/ApplicationId in build.gradle.kts
                        if (file.name.endsWith(".gradle.kts")) {
                            if (content.contains("namespace = \"$oldPkg\"")) {
                                content = content.replace("namespace = \"$oldPkg\"", "namespace = \"$newPkg\"")
                                modified = true
                            }
                            if (content.contains("applicationId = \"$oldPkg\"")) {
                                content = content.replace("applicationId = \"$oldPkg\"", "applicationId = \"$newPkg\"")
                                modified = true
                            }
                            if (content.contains("bundleId = \"$oldPkg\"")) {
                                content = content.replace("bundleId = \"$oldPkg\"", "bundleId = \"$newPkg\"")
                                modified = true
                            }
                            if (content.contains("baseName = \"$oldPkg\"")) {
                                content = content.replace("baseName = \"$oldPkg\"", "baseName = \"$newPkg\"")
                                modified = true
                            }
                        }
                        // Package name in other text files like README.md (if it was included)
                        else if (file.name == "README.md" && currentProject == project.rootProject) {
                            // Example: Replace fully qualified names if they appear
                            content = content.replace(oldPkg, newPkg) // General, be careful
                            modified = true
                        }
                        if (modified && content == originalContent) modified = false // check if regex actually changed something
                    }
                }


                // 2. App Name (strings.xml, Info.plist, README.md, etc.)
                if (oldApp.isNotEmpty()) {
                    if (file.name == "strings.xml" && content.contains(">$oldApp<")) {
                        content = content.replace(">$oldApp<", ">$newApp<")
                        modified = true
                    } else if (file.name == "Info.plist") {
                        val cfBundleNamePattern = Regex("(<key>CFBundleName</key>\\s*<string>)${Regex.escape(oldApp)}(</string>)")
                        val cfBundleDisplayNamePattern = Regex("(<key>CFBundleDisplayName</key>\\s*<string>)${Regex.escape(oldApp)}(</string>)")
                        var tempContent = content.replace(cfBundleNamePattern, "$1$newApp$2")
                        tempContent = tempContent.replace(cfBundleDisplayNamePattern, "$1$newApp$2")
                        if (tempContent != content) {
                            content = tempContent
                            modified = true
                        }
                    } else if (file.name == "README.md" && currentProject == project.rootProject) { // For root README
                        // Make this replacement more robust if needed (e.g., case insensitive, whole word)
                        if (content.contains(oldApp)) {
                            content = content.replace(oldApp, newApp)
                            modified = true
                        }
                    }
                }

                // 3. Prefix (Class names, file names - file renaming is separate, this is for content)
                if (oldPfx.isNotEmpty()) {
                    // Only apply to relevant file types like .kt, .java, .xml
                    if (file.extension in listOf("kt", "java", "xml")) {
                        val oldClassNamePattern = Regex("\\b${Regex.escape(oldPfx.capitalize())}")
                        if (content.contains(oldClassNamePattern)) {
                            content = content.replace(oldClassNamePattern, newPfx.capitalize())
                            modified = true
                        }
                        val oldLowerPrefixPattern = Regex("\\b${Regex.escape(oldPfx)}")
                        if (content.contains(oldLowerPrefixPattern)) {
                            content = content.replace(oldLowerPrefixPattern, newPfx)
                            modified = true
                        }
                    }
                }

                // 4. AndroidManifest.xml package attribute (specific to Android app module)
                if (file.name == "AndroidManifest.xml" && currentProject.path.contains("app")) { // Or a more reliable check for Android app module
                    if (oldPkg.isNotEmpty() && content.contains("package=\"$oldPkg\"")) {
                        content = content.replace("package=\"$oldPkg\"", "package=\"$newPkg\"")
                        modified = true
                    }
                }


                if (modified) {
                    logger.lifecycle("Updating content in: ${file.path} (from $logPrefix)")
                    try {
                         file.writeText(content) // UNCOMMENT FOR ACTUAL MODIFICATION
                        logger.lifecycle("SUCCESS: Updated content in ${file.name} (from $logPrefix)")
                        logger.info("MODIFIED (dry run) $logPrefix: ${file.path}\n--- OLD ---\n${originalContent.take(200)}\n--- NEW ---\n${content.take(200)}\n----------")
                    } catch (e: Exception) {
                        logger.error("ERROR writing file content ${file.path}: ${e.message}")
                    }
                }

                // --- Rename files based on prefix (only for modules, not typically root files) ---
                if (currentProject != project.rootProject && oldPfx.isNotEmpty() &&
                    (file.nameWithoutExtension.startsWith(oldPfx.capitalize()) || file.nameWithoutExtension.startsWith(oldPfx))) {
                    val oldFileName = file.name
                    var newFileName = oldFileName

                    if (oldFileName.startsWith(oldPfx.capitalize())) {
                        newFileName = newFileName.replaceFirst(oldPfx.capitalize(), newPfx.capitalize())
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


        // --- Update settings.gradle.kts for module names ---
        val settingsFile = project.rootProject.file("settings.gradle.kts")
        if (settingsFile.exists()) {
            var settingsContent = settingsFile.readText()
            var modifiedSettings = false
            // Example: if your module names are like ':oldPfx-featureA'
            if (oldPfx.isNotEmpty()) {
                val oldModulePattern = Regex("include\\s*\\(\\s*\"(.*)${Regex.escape(oldPfx)}(.*)\"\\s*\\)")
                // This is a simplified regex, adjust if your module names have more complex structures
                // and ensure the replacement correctly reconstructs the module path.
                // It's safer to target very specific module name patterns.
                settingsContent = settingsContent.replace(oldModulePattern) { matchResult ->
                    val before = matchResult.groupValues[1]
                    val after = matchResult.groupValues[2]
                    modifiedSettings = true
                    "include(\"$before$newPfx$after\")"
                }
            }
            // If app module itself uses oldPfx or oldAppName in its registered name
            // e.g. from include(":app") to include (":MyNewApp") if oldAppName was "app"
            // This requires careful handling of directory renames too.
            // Example for specific module name changes (safer):
            // val oldSpecificModuleName = ":${oldPfx}app"
            // val newSpecificModuleName = ":${newPfx}app"
            // if (settingsContent.contains("'$oldSpecificModuleName'")) {
            //    settingsContent = settingsContent.replace("'$oldSpecificModuleName'", "'$newSpecificModuleName'")
            //    modifiedSettings = true
            // }


            if (modifiedSettings) {
                logger.lifecycle("Updating module names in settings.gradle.kts.")
                 settingsFile.writeText(settingsContent) // UNCOMMENT FOR ACTUAL MODIFICATION
                logger.info("MODIFIED (dry run): ${settingsFile.path} with new module name patterns.")
                logger.lifecycle("IMPORTANT: Manual directory rename for affected modules might be needed.")
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
            rootProject.allprojects.find { it.name == "app" }?.file("src/androidMain/res/values/strings.xml"), // KMP Android
            rootProject.projectDir.resolve("app/src/main/res/values/strings.xml") // If task is run from root
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
            logger.warn("Cannot extract prefix because current package name is unknown.")
            return null
        }
        val packagePath = currentPackageName.replace('.', File.separatorChar)

        // Search for a common Application class pattern
        // Example: KmpApplication.kt, App.kt, MainApplication.kt
        // This is highly heuristic and needs to be adapted to your project structure.
        val commonAppClassNames = listOf("App", "Application", "MainApplication", "KmpApplication", currentPackageName.substringAfterLast('.').capitalize()+"App")

        for (proj in rootProject.allprojects) {
            val kotlinSrcDirs = listOf(
                "src/main/kotlin", "src/commonMain/kotlin", "src/androidMain/kotlin",
                "src/iosMain/kotlin", "src/desktopMain/kotlin", "src/jvmMain/kotlin"
            ).map { proj.projectDir.resolve(it).resolve(packagePath) }

            for (srcDir in kotlinSrcDirs) {
                if (srcDir.exists() && srcDir.isDirectory) {
                    for (appClassNameBase in commonAppClassNames) {
                        val appFile = srcDir.resolve("$appClassNameBase.kt")
                        if (appFile.exists()) {
                            val content = appFile.readText()
                            // Heuristic: if class name starts with 2-4 uppercase letters followed by "App" or "Application"
                            // e.g., KMTApp, NCAApplication. This is very basic.
                            val prefixPattern = Regex("""class\s+([A-Z]{2,4})(${appClassNameBase.removePrefixMatches(Regex("[A-Z]{2,4}"))})\s*\(""")
                            prefixPattern.find(content)?.groupValues?.get(1)?.toLowerCase()?.let {
                                logger.lifecycle("Deduced old prefix '$it' from class ${appFile.name}")
                                return it
                            }
                        }
                    }
                }
            }
        }
        logger.warn("Could not automatically determine old class prefix. This is often project-specific.")
        return null // Or a default/fallback
    }
}

// Helper extension for the prefix extraction logic
private fun String.removePrefixMatches(regex: Regex): String {
    var current = this
    while (regex.matchesAt(current, 0)) {
        val match = regex.find(current) ?: break
        current = current.substring(match.range.last + 1)
    }
    return current
}
