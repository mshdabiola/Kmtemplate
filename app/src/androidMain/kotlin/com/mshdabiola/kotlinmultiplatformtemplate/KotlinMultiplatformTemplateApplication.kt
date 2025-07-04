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
package com.mshdabiola.kotlinmultiplatformtemplate

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.kotlinmultiplatformtemplate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KotlinMultiplatformTemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val logger =
            Logger(
                loggerConfigInit(platformLogWriter()),
                "AndroidLogger",
            )
        val logModule =
            module {
                single {
                    logger
                }
            }

        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@KotlinMultiplatformTemplateApplication)
            modules(appModule, logModule)
        }

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
}
