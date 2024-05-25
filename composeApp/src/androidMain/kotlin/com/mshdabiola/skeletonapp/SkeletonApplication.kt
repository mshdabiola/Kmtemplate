/*
 *abiola 2024
 */

package com.mshdabiola.skeletonapp

import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import co.touchlab.kermit.loggerConfigInit
import co.touchlab.kermit.platformLogWriter
import com.mshdabiola.model.Writer
import com.mshdabiola.skeletonapp.di.appModule
import com.mshdabiola.skeletonapp.di.jankStatsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val logger = Logger(
            loggerConfigInit(platformLogWriter()),
            "AndroidLogger",
        )
        val logModule = module {
            single {
                logger
            }
        }

        startKoin {
            logger(
                KermitKoinLogger(Logger.withTag("koin")),
            )
            androidContext(this@SkeletonApplication)
            modules(appModule, jankStatsModule,logModule)
        }

//        if (packageName.contains("debug")) {
//            Timber.plant(Timber.DebugTree())
//            Timber.e("log on app create")
//        }
    }
}
