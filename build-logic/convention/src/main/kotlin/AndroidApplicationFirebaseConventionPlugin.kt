/*
 * Copyright (C) 2025 MshdAbiola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 */
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import com.google.firebase.perf.plugin.FirebasePerfExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.gms.google-services")
                apply("com.google.firebase.firebase-perf")
                apply("com.google.firebase.crashlytics")
            }

            extensions.configure<ApplicationAndroidComponentsExtension> {
                finalizeDsl {
                    it.productFlavors.forEach { flavor ->
                        val isGoogle = flavor.name
                            .contains("google", true)
                        flavor.configure<FirebasePerfExtension> {
                            setInstrumentationEnabled(isGoogle)
                        }
                        flavor.configure<CrashlyticsExtension> {
                            mappingFileUploadEnabled = isGoogle
                        }
                        //   println("flavor ${flavor.name}")
                    }
//                    it.buildTypes.forEach { buildType ->
//                        // Disable the Crashlytics mapping file upload. This feature should only be
//                        // enabled if a Firebase backend is available and configured in
//                        // google-services.json.
//                        buildType.configure<FirebasePerfExtension> {
//                            setInstrumentationEnabled(false)
//                        }
//                        buildType.configure<CrashlyticsExtension> {
//                            println("buildType $buildType")
//                            mappingFileUploadEnabled = !buildType.isDebuggable
//                        }
//                    }
                }
            }
        }
    }
}
