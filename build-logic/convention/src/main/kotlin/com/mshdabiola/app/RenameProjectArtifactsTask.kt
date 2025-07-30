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
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory

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
        val oldPkg = extractPackageFromAppBuildGradle(rootProject = project.rootProject)?:""
        val oldApp = extractAppNameFromStringsXml(rootProject = project.rootProject)?:""
        val oldPfx = extractPrefixFromApplicationKt(rootProject = project.rootProject, currentPackageName = oldPkg)?:""

        logger.lifecycle("Starting project artifact renaming...")
        logger.lifecycle("Old Package: $oldPkg -> New Package: $newPkg")
        logger.lifecycle("Old App Name: $oldApp -> New App Name: $newApp")
        logger.lifecycle("Old Prefix: $oldPfx -> New Prefix: $newPfx")


        // --- Helper for path conversion ---
        val oldPkgPath = oldPkg.replace('.', File.separatorChar)
        val newPkgPath = newPkg.replace('.', File.separatorChar)


        val packageRelevantSourceRoots = listOf(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin", "src/androidTest/kotlin", "src/androidUnitTest/kotlin",
            "src/iosMain/kotlin", "src/iosTest/kotlin",
            "src/desktopMain/kotlin", // If applicable
            "src/jvmMain/kotlin", "src/jvmTest/kotlin",
            "src/main/kotlin", "src/main/java", // Standard JVM/Android
            "src/test/kotlin", "src/test/java",
            "src", // Catch-all for simple structures like android app's direct src
        )


        // --- 1. Rename Directories (Package Structure) ---
        logger.lifecycle("Phase 1: Renaming Directories")
        project.logger.lifecycle("--- Phase 1: Renaming Directories ---")
        (project.rootProject.allprojects).forEach { proj -> // proj is of type Project
            project.logger.lifecycle("Checking for directory renames in project: ${proj.path}")
            packageRelevantSourceRoots.forEach { srcRootRelativePath ->
                val baseSrcDir = proj.projectDir.resolve(srcRootRelativePath)
                if (baseSrcDir.exists() && baseSrcDir.isDirectory) {
                    val oldPackageDir = baseSrcDir.resolve(oldPkgPath)
                    if (oldPackageDir.exists() && oldPackageDir.isDirectory && oldPkgPath.isNotEmpty()) { // Ensure oldPkgPath is not empty
                        val newPackageDir = baseSrcDir.resolve(newPkgPath)
                        if (oldPackageDir.path != newPackageDir.path) { // Avoid renaming to itself
                            if (newPackageDir.exists()) {
                                project.logger.warn("WARNING: Target package directory ${newPackageDir.path} already exists in project ${proj.path}. Manual merge might be needed.")
                            } else {
                                newPackageDir.parentFile.mkdirs() // Ensure parent directories exist
                                project.logger.lifecycle("Attempting to rename directory: ${oldPackageDir.path} to ${newPackageDir.path}")
                                // ### ACTUAL MODIFICATION - UNCOMMENT WITH CAUTION ###
                                if (oldPackageDir.renameTo(newPackageDir)) {
                                    project.logger.lifecycle("SUCCESS: Renamed directory in ${proj.path}: ${oldPackageDir.name} to ${newPackageDir.name}")
                                } else {
                                    project.logger.error("ERROR: Failed to rename directory ${oldPackageDir.path} to ${newPackageDir.path} in project ${proj.path}. Check permissions or if files are in use.")
                                }
                            }
                        }
                    }
                }
            }
        }
        // Cleanup empty parent directories if the old package structure was deeper
        // This requires careful implementation to avoid deleting wanted files.


        // --- 2. Update File Contents & 3. Rename Files ---
        logger.lifecycle("Phase 2 & 3: Updating File Contents and Renaming Files")
        logger.lifecycle("Phase 2 & 3: Updating File Contents and Renaming Files")
        (project.rootProject.allprojects).forEach { proj -> // proj is of type Project
            project.logger.lifecycle("Scanning for file updates/renames in project: ${proj.path}")

            val filesToProcess: ConfigurableFileTree = project.fileTree(proj.projectDir) {
                include(
                    "**/*.kt", "**/*.java",
                    "**/*.xml",
                    "**/*.gradle.kts",
                    "**/Info.plist",
                    "**/project.pbxproj", // iOS Xcode project file (handle with EXTREME caution)
                    "**/Project.swift",   // Swift Package Manager config
                    "**/settings.gradle.kts", // Should only exist in root, but include is harmless
                    // Add other text-based file patterns you need to process
                )
                exclude(
                    // CRITICAL: Prevent processing unwanted/binary files and directories
                    "**/build/**",
                    "**/.gradle/**",
                    "**/.idea/**",
                    "**/.git/**",
                    "**/.vs/**", // Visual Studio
                    "**/.vscode/**", // VS Code
                    "**/.DS_Store",
                    "**/*.iml", // IntelliJ module files
                    "**/local.properties",
                    "**/*.a", "**/*.so", "**/*.dylib", "**/*.framework/**", // Native binaries
                    "**/*.jar", "**/*.aar", "**/*.class", "**/*.dex",      // JVM/Android binaries
                    "**/*.zip", "**/*.gz", "**/*.tar", "**/*.rar",         // Archives
                    "**/*.png", "**/*.jpg", "**/*.jpeg", "**/*.gif", "**/*.svg", // Images
                    "**/*.mp3", "**/*.mp4", "**/*.wav", "**/*.ogg",       // Media files
                    "**/Pods/**", // Cocoapods directory
                    "**/Carthage/**", // Carthage directory
                    "**/node_modules/**", // Node.js dependencies
                    // Add more specific exclusions for your project if needed
                )
            }

            filesToProcess.forEach { file ->
                var content: String
                try {
                    // Basic check to avoid reading huge binary files that might have slipped through excludes
                    if (file.length() > 20 * 1024 * 1024) { // Skip files larger than 20MB
                        project.logger.debug("Skipping potentially large/binary file: ${file.path}")
                        return@forEach
                    }
                    content = file.readText()
                } catch (e: Exception) {
                    project.logger.debug("Skipping non-text or unreadable file: ${file.path} (Reason: ${e.message})")
                    return@forEach // Skip to next file
                }

                var modified = false
                val originalContent = content // For comparison if logging changes

                // 1. Replace package statements and imports (Only for .kt and .java files)
                if (file.extension == "kt" || file.extension == "java") {
                    if (oldPkg.isNotEmpty() && content.contains("package $oldPkg")) {
                        content = content.replace("package $oldPkg", "package $newPkg")
                        modified = true
                    }
                    if (oldPkg.isNotEmpty() && content.contains("import $oldPkg")) {
                        // Regex to handle imports like 'import oldPkg.Class', 'import oldPkg.*', 'import oldPkg.sub.Class'
                        content = content.replace(Regex("import\\s+${Regex.escape(oldPkg)}(\\.\\*|\\.[A-Za-z0-9_]+(?:\\.[A-Za-z0-9_]+)*(\\.\\*)?)"), "import $newPkg$1")
                        modified = true
                    }
                }

                // 2. Replace App Name
                // Example for strings.xml (Android)
                if (file.name == "strings.xml" && content.contains(">$oldApp<")) {
                    content = content.replace(">$oldApp<", ">$newApp<")
                    modified = true
                }
                // Example for Info.plist (iOS) - CFBundleName, CFBundleDisplayName
                if (file.name == "Info.plist") { // Typically in iosApp or shared framework resources
                    if (content.contains("<string>$oldApp</string>")) { // Simple match first
                        content = content.replace("<string>$oldApp</string>", "<string>$newApp</string>")
                        modified = true
                    }
                    // More specific for CFBundleName and CFBundleDisplayName if simple replace is too broad
                    val cfBundleNamePattern = Regex("(<key>CFBundleName</key>\\s*<string>)${Regex.escape(oldApp)}(</string>)")
                    val cfBundleDisplayNamePattern = Regex("(<key>CFBundleDisplayName</key>\\s*<string>)${Regex.escape(oldApp)}(</string>)")
                    content = content.replace(cfBundleNamePattern, "$1$newApp$2")
                    content = content.replace(cfBundleDisplayNamePattern, "$1$newApp$2")
                    if (content != originalContent && !modified) modified = true // If regex changed it
                }

                // 3. Replace Prefix in class names, variable names, etc. (Use with EXTREME caution)
                if (oldPfx.isNotEmpty()) {
                    val oldClassNamePattern = Regex("\\b${Regex.escape(oldPfx.capitalize())}")
                    if (content.contains(oldClassNamePattern)) {
                        content = content.replace(oldClassNamePattern, newPfx.capitalize())
                        modified = true
                    }
                    val oldLowerPrefixPattern = Regex("\\b${Regex.escape(oldPfx)}") // For variables, XML IDs, etc.
                    if (content.contains(oldLowerPrefixPattern)) {
                        content = content.replace(oldLowerPrefixPattern, newPfx)
                        modified = true
                    }
                }

                // 4. Update AndroidManifest.xml package attribute (if not using namespace from build.gradle)
                if (file.name == "AndroidManifest.xml" && proj.path.contains("app")) { // Be specific
                    if (oldPkg.isNotEmpty() && content.contains("package=\"$oldPkg\"")) {
                        content = content.replace("package=\"$oldPkg\"", "package=\"$newPkg\"")
                        modified = true
                    }
                }

                // 5. Update build.gradle.kts (applicationId, bundleId, namespace)
                if (file.name.endsWith(".gradle.kts")) {
                    // Namespace (Common for Android library modules and app modules)
                    if (oldPkg.isNotEmpty() && content.contains("namespace = \"$oldPkg\"")) {
                        content = content.replace("namespace = \"$oldPkg\"", "namespace = \"$newPkg\"")
                        modified = true
                    }
                    // ApplicationId (Typically in app module's build.gradle.kts)
                    if (proj.name.contains("app") || proj.plugins.hasPlugin("com.android.application")) { // Heuristic for app module
                        if (oldPkg.isNotEmpty() && content.contains("applicationId = \"$oldPkg\"")) {
                            content = content.replace("applicationId = \"$oldPkg\"", "applicationId = \"$newPkg\"")
                            modified = true
                        }
                    }
                    // BundleId (Common for iOS KMP framework or direct bundleId declaration)
                    // This is very project-specific. Adjust pattern as needed.
                    if (oldPkg.isNotEmpty() && content.contains("bundleId = \"$oldPkg\"")) {
                        content = content.replace("bundleId = \"$oldPkg\"", "bundleId = \"$newPkg\"")
                        modified = true
                    } else if (oldPkg.isNotEmpty() && content.contains("baseName = \"$oldPkg\"")) { // e.g. framework { baseName = ... }
                        content = content.replace("baseName = \"$oldPkg\"", "baseName = \"$newPkg\"")
                        modified = true
                    }
                }

                // --- Write content if modified ---
                if (modified) {
                    project.logger.lifecycle("Updating content in: ${file.path}")
                    // ### ACTUAL MODIFICATION - UNCOMMENT WITH CAUTION ###
                    try {
                         file.writeText(content)
                         project.logger.lifecycle("SUCCESS: Updated content in ${file.name} in project ${proj.path}")
                        project.logger.info("MODIFIED (dry run): ${file.path}\n--- OLD ---\n${originalContent.take(200)}\n--- NEW ---\n${content.take(200)}\n----------")

                    } catch (e: Exception) {
                        project.logger.error("ERROR writing file content ${file.path}: ${e.message}")
                    }
                }

                // --- Rename files based on prefix ---
                // Process 'file' which is the original file from fileTree
                if (oldPfx.isNotEmpty() && (file.nameWithoutExtension.startsWith(oldPfx.capitalize()) || file.nameWithoutExtension.startsWith(oldPfx))) {
                    val oldFileName = file.name
                    var newFileName = oldFileName

                    if (oldFileName.startsWith(oldPfx.capitalize())) {
                        newFileName = newFileName.replaceFirst(oldPfx.capitalize(), newPfx.capitalize())
                    } else if (oldFileName.startsWith(oldPfx)) {
                        newFileName = newFileName.replaceFirst(oldPfx, newPfx)
                    }

                    if (oldFileName != newFileName) {
                        val newFile = File(file.parentFile, newFileName)
                        project.logger.lifecycle("Attempting to rename file: ${file.path} to ${newFile.path}")
                        // ### ACTUAL MODIFICATION - UNCOMMENT WITH CAUTION ###
                         if (file.renameTo(newFile)) {
                            project.logger.lifecycle("SUCCESS: Renamed file in ${proj.path}: ${file.name} to ${newFile.name}")
                            // If further operations needed on this renamed file, update the 'file' variable
                            // or handle carefully as the original 'file' object points to the old path.
                         } else {
                            project.logger.error("ERROR: Failed to rename file ${file.path} to ${newFile.path} in project ${proj.path}")
                         }
                        project.logger.info("RENAMED (dry run): ${file.path} -> ${newFile.path}")
                    }
                }
            }
        }


        // --- 4. Update settings.gradle.kts for module names (if applicable) ---
        // This is more complex and depends on how your modules are named.
        // If module names contain the oldPrefix or oldAppName.
        // Example: include(":${oldPfx}App") -> include(":${newPfx}App")
        val settingsFile = project.rootProject.file("settings.gradle.kts")
        if (settingsFile.exists()) {
            var settingsContent = settingsFile.readText()
            val oldModuleName = ":${oldPfx}app" // Adjust based on your module naming
            val newModuleName = ":${newPfx}app" // Adjust

            if (settingsContent.contains("'$oldModuleName'")) {
                settingsContent = settingsContent.replace("'$oldModuleName'", "'$newModuleName'")
                settingsFile.writeText(settingsContent)
                logger.lifecycle("Updated module name in settings.gradle.kts from $oldModuleName to $newModuleName. Manual directory rename might be needed for $oldModuleName.")
            }
            // Also check for module names like ':app' if you intend to rename the main app module itself
            // This would also require renaming the actual directory 'app/' to 'newAppName/'
        }


        logger.lifecycle("Renaming process finished.")
        logger.lifecycle("--------------------------------------------------------------------")
        logger.lifecycle("IMPORTANT MANUAL STEPS REQUIRED:")
        logger.lifecycle("- Review ALL changes carefully using your version control (git diff).")
        logger.lifecycle("- If module names were changed in settings.gradle.kts, RENAME THE ACTUAL MODULE DIRECTORIES accordingly.")
        logger.lifecycle("- In Android Studio/IntelliJ: File -> Invalidate Caches / Restart.")
        logger.lifecycle("- Perform a clean build: ./gradlew clean build (or assemble)")
        logger.lifecycle("- Manually double-check AndroidManifest.xml (especially if 'package' attribute was used directly).")
        logger.lifecycle("- Manually double-check Info.plist (iOS) for CFBundleIdentifier, CFBundleName, CFBundleDisplayName.")
        logger.lifecycle("- Thoroughly TEST your application on all platforms.")
        logger.lifecycle("--------------------------------------------------------------------")

    }
    /**
     * Attempts to parse the group from the app/build.gradle.kts file.
     * This is a fallback and can be brittle.
     */
    private fun extractPackageFromAppBuildGradle(rootProject: Project): String? {
        val appBuildFile = rootProject.file("app/build.gradle.kts")
        if (appBuildFile.exists()) {
            try {
                val content = appBuildFile.readText()
                // Regex to find: group = "your.package.name"
                // It handles potential spaces around '=' and quotes.
                val pattern = Pattern.compile("""^\s*group\s*=\s*["']([^"']+)["']""", Pattern.MULTILINE)
                val matcher = pattern.matcher(content)
                if (matcher.find()) {
                    val foundGroup = matcher.group(1)
                    if (foundGroup.isNotBlank()) {
                        rootProject.logger.info("CiTaskPlugin: Extracted oldPackageName '$foundGroup' from app/build.gradle.kts")
                        return foundGroup
                    }
                }
            } catch (e: Exception) {
                rootProject.logger.warn("CiTaskPlugin: Could not read or parse app/build.gradle.kts for group: ${e.message}")
            }
        } else {
            rootProject.logger.warn("CiTaskPlugin: app/build.gradle.kts not found for extracting default oldPackageName.")
        }
        return null // Return null if not found or error, so orElse can take over
    }

    private fun extractAppNameFromStringsXml(rootProject: Project): String? {
        // Correct the path to strings.xml based on your project structure
        val stringsXmlFile = rootProject.file("app/src/androidMain/res/values/strings.xml") // Path based on your context
        if (stringsXmlFile.exists()) {
            try {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc = dBuilder.parse(stringsXmlFile)
                doc.documentElement.normalize()

                val nodeList = doc.getElementsByTagName("string")
                for (i in 0 until nodeList.length) {
                    val node = nodeList.item(i)
                    if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        val element = node as org.w3c.dom.Element
                        if (element.getAttribute("name") == "app_name") {
                            val appName = element.textContent
                            if (appName.isNotBlank()) {
                                rootProject.logger.info("CiTaskPlugin: Extracted oldAppName '$appName' from strings.xml")
                                return appName
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                rootProject.logger.warn("CiTaskPlugin: Could not read or parse strings.xml for app_name: ${e.message}")
                // Log the full stack trace for debugging if needed, e.g., e.printStackTrace()
            }
        } else {
            rootProject.logger.warn("CiTaskPlugin: app/src/androidMain/res/values/strings.xml not found for extracting default oldAppName.")
        }
        return null
    }
    private fun extractPrefixFromApplicationKt(rootProject: Project, currentPackageName: String): String? {
        if (currentPackageName.isBlank()) {
            rootProject.logger.warn("CiTaskPlugin: Cannot extract prefix from Application.kt without a valid oldPackageName.")
            return null
        }

        val packagePath = currentPackageName.replace('.', File.separatorChar)
        val appKotlinDir = rootProject.file("app/src/androidMain/kotlin/$packagePath")

        if (appKotlinDir.exists() && appKotlinDir.isDirectory) {
            // Look for a file ending with "Application.kt"
            // Example: NdaApplication.kt -> Nda
            // Example: MyCoolApplication.kt -> MyCool
            val appFile = appKotlinDir.listFiles { _, name -> name.endsWith("Application.kt") }?.firstOrNull()

            if (appFile != null) {
                val filenameWithoutExtension = appFile.nameWithoutExtension // E.g., "NdaApplication"
                if (filenameWithoutExtension.endsWith("Application")) {
                    val potentialPrefix = filenameWithoutExtension.removeSuffix("Application")
                    if (potentialPrefix.isNotBlank()) {
                        rootProject.logger.info("CiTaskPlugin: Extracted oldPrefix '$potentialPrefix' from ${appFile.name}")
                        // You might want to return it as lowercase: potentialPrefix.toLowerCase()
                        // depending on how you use the "prefix" (e.g., kmt vs Nda)
                        return potentialPrefix // Returning "Nda"
                    }
                }
            } else {
                rootProject.logger.info("CiTaskPlugin: No '*Application.kt' file found in $appKotlinDir to infer oldPrefix.")
            }
        } else {
            rootProject.logger.warn("CiTaskPlugin: Application Kotlin directory not found at $appKotlinDir for extracting prefix.")
        }
        return null
    }
}
