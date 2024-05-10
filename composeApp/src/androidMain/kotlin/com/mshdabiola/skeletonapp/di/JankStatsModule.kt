/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.di

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.JankStats.OnFrameListener
import org.koin.dsl.module

val jankStatsModule = module {

    single {
        OnFrameListener { frameData ->
            // Make sure to only log janky frames.
            if (frameData.isJank) {
                // We're currently logging this but would better report it to a backend.
                Log.v("NiA Jank", frameData.toString())
            }
        }
    }

    single {
        val context: Context = get()
        JankStats.createAndTrack((context as Activity).window, get())
    }
}
