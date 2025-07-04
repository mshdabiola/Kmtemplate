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
