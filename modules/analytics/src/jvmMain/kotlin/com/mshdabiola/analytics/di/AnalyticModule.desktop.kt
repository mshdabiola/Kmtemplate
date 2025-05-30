package com.mshdabiola.analytics.di

import com.mshdabiola.analytics.AnalyticsEvent
import com.mshdabiola.analytics.AnalyticsHelper
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val analyticsModule: Module
    get() =
        module {

            single {
                object : AnalyticsHelper {
                    override fun logEvent(event: AnalyticsEvent) {
                        println("Logging event: $event")
                    }
                }
            } bind AnalyticsHelper::class
        }
