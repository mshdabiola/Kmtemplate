/*
 * Copyright (C) 2022-2025 MshdAbiola
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
package com.mshdabiola.benchmarks.startup

import androidx.benchmark.macro.BaselineProfileMode.Disable
import androidx.benchmark.macro.BaselineProfileMode.Require
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode.COLD
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.mshdabiola.benchmarks.PACKAGE_NAME
import com.mshdabiola.benchmarks.main.mainWaitForContent
import com.mshdabiola.benchmarks.startActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Enables app startups from various states of baseline profile or [CompilationMode]s.
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance from a cold state.
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupWithoutPreCompilation() = startup(CompilationMode.None())

    @Test
    fun startupWithPartialCompilationAndDisabledBaselineProfile() =
        startup(
            CompilationMode.Partial(baselineProfileMode = Disable, warmupIterations = 1),
        )

    @Test
    fun startupPrecompiledWithBaselineProfile() = startup(CompilationMode.Partial(baselineProfileMode = Require))

    @Test
    fun startupFullyPrecompiled() = startup(CompilationMode.Full())

    private fun startup(compilationMode: CompilationMode) =
        benchmarkRule.measureRepeated(
            packageName = PACKAGE_NAME,
            metrics = listOf(StartupTimingMetric()),
            compilationMode = compilationMode,
            // More iterations result in higher statistical significance.
            iterations = 20,
            startupMode = COLD,
            setupBlock = {
                pressHome()
                //  allowNotifications()
            },
        ) {
            startActivity()
            // Waits until the content is ready to capture Time To Full Display
            mainWaitForContent()
        }
}
