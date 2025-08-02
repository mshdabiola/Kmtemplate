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
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.koin.kermitLoggerModule
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.kmtemplate.di.appModule
import org.acra.ReportField
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KmtApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val logger =
            Logger(
                loggerConfigInit(
                    minSeverity = Severity.Error,
                    logWriters = arrayOf(platformLogWriter(DefaultFormatter)),
                ),
            )

        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@KmtApplication)
            modules(appModule, kermitLoggerModule(logger))
        }
        setupCrashReporter()

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
    private fun setupCrashReporter() {
        Thread {
//            if (true) {
            initAcra {
                reportFormat = StringFormat.KEY_VALUE_LIST
                reportContent = listOf(
                    ReportField.REPORT_ID, ReportField.APP_VERSION_NAME,
                    ReportField.PHONE_MODEL, ReportField.BRAND, ReportField.PRODUCT, ReportField.ANDROID_VERSION,
                    ReportField.BUILD_CONFIG, ReportField.STACK_TRACE, ReportField.LOGCAT,
                )
                mailSender {
                    reportAsFile = true
                    mailTo = getString(R.string.email)
                    subject = getString(R.string.crash_title)
                    body = getString(R.string.crash_body)
                    reportFileName = "Kmtemplate_Bug_Report.txt"
                }
            }
//            } else {
//                System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
// //                Timber.plant(Timber.DebugTree())
//            }
        }.start()
    }
}
