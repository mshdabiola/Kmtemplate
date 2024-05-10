/*
 *abiola 2024
 */

package com.mshdabiola.skeletonapp

import android.app.Application
import com.mshdabiola.skeletonapp.di.appModule
import com.mshdabiola.skeletonapp.di.jankStatsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkeletonApplication)
            modules(appModule, jankStatsModule)
        }

        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }
    }
}
