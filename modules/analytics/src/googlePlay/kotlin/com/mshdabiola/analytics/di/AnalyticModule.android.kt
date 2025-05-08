package com.mshdabiola.analytics.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
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
                    val firebaseAnalytics = Firebase.analytics

                    override fun logEvent(event: AnalyticsEvent) {
                    }
                }
            } bind AnalyticsHelper::class
        }
