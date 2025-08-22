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
package com.mshdabiola.kmtemplate

import android.app.Application
import android.os.Build
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.koin.kermitLoggerModule
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.kmtemplate.di.appModule
import com.mshdabiola.model.BuildType
import com.mshdabiola.model.Flavor
import com.mshdabiola.model.Platform
import org.acra.ReportField
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class KmtApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val platform = getPlatform()

        val logger =
            Logger(
                loggerConfigInit(
                    minSeverity = if (platform.buildType == BuildType.Debug) {
                        Severity.Verbose
                    } else {
                        Severity.Error
                    },
                    logWriters = arrayOf(platformLogWriter(DefaultFormatter)),
                ),
            )
        val applicationModule = module {
            single { platform } bind Platform::class
        }
        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@KmtApplication)
            modules(
                appModule,
                kermitLoggerModule(logger),
                applicationModule,
            )
        }
        if (platform.flavor == Flavor.FossReliant) {
            setupCrashReporter()
        }
    }

    private fun setupCrashReporter() {
        Thread {
            initAcra {
                reportFormat = StringFormat.KEY_VALUE_LIST
                reportContent = listOf(
                    ReportField.REPORT_ID, ReportField.APP_VERSION_NAME,
                    ReportField.PHONE_MODEL, ReportField.BRAND, ReportField.PRODUCT, ReportField.ANDROID_VERSION,
                    ReportField.BUILD_CONFIG, ReportField.STACK_TRACE, ReportField.LOGCAT,
                )
                mailSender {
                    reportAsFile = true
                    mailTo = com.mshdabiola.model.BuildConfig.DEVELOPER_EMAIL
                    subject = getString(R.string.crash_title)
                    body = getString(R.string.crash_body)
                    reportFileName = "Kmtemplate_Bug_Report.txt"
                }
            }
        }.start()
    }

    private fun getPlatform(): Platform.Android {
        val sdk = Build.VERSION.SDK_INT

        return Platform.Android(BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE, sdk)
    }
}
